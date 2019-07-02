package com.example.myproject.web;

import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.comm.Const;
import com.example.myproject.comm.aop.LoggerManage;
import com.example.myproject.domain.Party;
import com.example.myproject.domain.User;
import com.example.myproject.domain.result.ExceptionMsg;
import com.example.myproject.domain.result.Response;
import com.example.myproject.domain.result.ResponseData;
import com.example.myproject.repository.PartyRepository;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.utils.DateUtils;
import com.example.myproject.utils.MD5Util;
import com.example.myproject.utils.MessageUtil;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PartyRepository partyRepository;	
	@Resource
    private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String mailFrom;
	@Value("密码重置邮件")
	private String mailSubject;
	@Value("请点击以下地址:<br/><a href='{0}'>重置密码</a>")
	private String mailContent;
	@Value("${forgotpassword.url}")
	private String forgotpasswordUrl;
	@Value("${static.url}")
	private String staticUrl;
	@Value("${file.profilepictures.url}")
	private String fileProfilepicturesUrl;
	@Value("${file.backgroundpictures.url}")
	private String fileBackgroundpicturesUrl;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@LoggerManage(description = "登陆")
	public ResponseData login(User user, HttpServletResponse response) {
		try {
			//这里不是bug，前端userName有可能是邮箱也有可能是昵称。user.getUserName()从前端返回用户名
			User loginUser = userRepository.findByUserNameOrEmail(user.getUserName(), user.getUserName());
			if (loginUser == null) {
				return new ResponseData(ExceptionMsg.LoginNameNotExists);
			} else if (!loginUser.getPassword().equals(getPwd(user.getPassword()))) {
				return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
			}
			Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUser.getId().toString()));
			cookie.setMaxAge(Const.COOKIE_TIMEOUT);
			cookie.setPath("/");
			response.addCookie(cookie);
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
			String preUrl = "/home";
			return new ResponseData(ExceptionMsg.SUCCESS,preUrl);
		} catch (Exception e) {
			logger.error("login failed", e);
			return new ResponseData(ExceptionMsg.FAILED);
		}

	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@LoggerManage(description = "注册")
	public Response create(User user) {
		try {
			User registUser = userRepository.findByEmail(user.getEmail());
			if (registUser!= null) {
				return result(ExceptionMsg.EmailUsed);
			}
			User userNameUser = userRepository.findByUserName(user.getUserName());
			if (userNameUser != null) {
				return new ResponseData(ExceptionMsg.UserNameUsed);
			}
			user.setPassword(getPwd(user.getPassword()));
			user.setCreateTime(DateUtils.getCurrentTime());
			user.setLastModifyTime(DateUtils.getCurrentTime());
			user.setProfilePicture("img/potrait.png");
			userRepository.save(user);
			// 添加默认收藏夹
			//Favorites favorites = favoritesService.saveFavorites(user.getId(), "未读列表");
			// 添加默认属性设置
			//configService.saveConfig(user.getId(),String.valueOf(favorites.getId()));	
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
			
		} catch (Exception e) {
			logger.error("create user failed", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
//	@RequestMapping(value = "/getConfig",method = RequestMethod.POST)
//	@LoggerManage(description ="获取属性设置")
//	public Config getConfig(){
//		Config config = new Config();
//		try{
//			config = configRepository.findByUserId(getUserId());
//		}catch(Exception e){
//			logger.error("异常:",e);
//		}
//		return config;
//	}
	
	/**
	 * 忘记密码-发送重置邮件
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/sendForgotPasswordEmail", method = RequestMethod.POST)
	@LoggerManage(description="发送忘记密码邮件")
	public Response sendForgotPasswordEmail(String email) {
		try {
			User registUser = userRepository.findByEmail(email);
			if (null == registUser) {
				return result(ExceptionMsg.EmailNotRegister);
			}	
			String secretKey = UUID.randomUUID().toString(); // 密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;
            userRepository.setOutDateAndValidataCode(outDate+"", secretKey, email);
            String key =email + "$" + date + "$" + secretKey;
            String digitalSignature = MD5Util.encrypt(key);// 数字签名
//            String basePath = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort() + this.getRequest().getContextPath() + "/newPassword";
            String resetPassHref = forgotpasswordUrl + "?sid="
                    + digitalSignature +"&email="+email;
            String emailContent = MessageUtil.getMessage(mailContent, resetPassHref);					
	        MimeMessage mimeMessage = mailSender.createMimeMessage();	        
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setFrom(mailFrom);
	        helper.setTo(email);
	        helper.setSubject(mailSubject);
	        helper.setText(emailContent, true);
	        mailSender.send(mimeMessage);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("sendForgotPasswordEmail failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	
	/**
	 * 忘记密码-设置新密码
	 * @param newpwd
	 * @param email
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
	@LoggerManage(description="设置新密码")
	public Response setNewPassword(String newpwd, String email, String sid) {
		try {
			User user = userRepository.findByEmail(email);
			Timestamp outDate = Timestamp.valueOf(user.getOutDate());
			if(outDate.getTime() <= System.currentTimeMillis()){ //表示已经过期
				return result(ExceptionMsg.LinkOutdated);
            }
            String key = user.getEmail()+"$"+outDate.getTime()/1000*1000+"$"+user.getValidataCode();//数字签名
            String digitalSignature = MD5Util.encrypt(key);
            if(!digitalSignature.equals(sid)) {
            	 return result(ExceptionMsg.LinkOutdated);
            }
            
            userRepository.setNewPassword(getPwd(newpwd), email);
            user.setPassword(getPwd(newpwd));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("setNewPassword failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@LoggerManage(description="修改密码")
	public Response updatePassword(String oldPassword, String newPassword) {
		try {
			User user = getUser();
			String password = user.getPassword();
			String newpwd = getPwd(newPassword);
			if(password.equals(getPwd(oldPassword))){
				userRepository.setNewPassword(newpwd, user.getEmail());
				user.setPassword(newpwd);
				getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
			}else{
				return result(ExceptionMsg.PassWordError);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("updatePassword failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	/**
	 * 创建聚会
	 * @param partyStartTime
	 * @param partyEndTime
	 * @param partyAddress
	 * @param headCount
	 * @param partyType
	 * @param estimateCost
	 * @param partyDescription
	 * @return
	 */
	@RequestMapping(value = "/createParty", method = RequestMethod.POST)
	@LoggerManage(description="创建聚会")
	public Response createParty(String partyStartTime, String partyEndTime, 
			                       String partyAddress,String headCount, String partyType,
			                       String estimateCost, String partyDescription) {
		try {
			User user = getUser();
			Party party = new Party();
            party.setPartyStartTime(partyStartTime);
            party.setPartyEndTime(partyEndTime);
            party.setPartyAddress(partyAddress);
            party.setHeadCount(headCount);
            party.setPartyType(partyType);
            party.setEstimateCost(estimateCost);
            party.setPartyDescription(partyDescription);
            party.setUser(userRepository.findByUserName(user.getUserName()));
            partyRepository.save(party);
			user.addToPartyCreatedList(party);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("createParty failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	
}