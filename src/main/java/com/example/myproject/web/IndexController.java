package com.example.myproject.web;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.myproject.comm.Const;
import com.example.myproject.comm.aop.LoggerManage;
import com.example.myproject.domain.Party;
import com.example.myproject.domain.User;
import com.example.myproject.repository.PartyRepository;
import com.example.myproject.repository.UserRepository;


//页面索引跳转控制器
@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	@Autowired
    private PartyRepository partyRepository;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
    @LoggerManage(description="首页")
	public String index(Model model){
		User user = super.getUser();
		if(null!=user){
			model.addAttribute("user", user);
		}
		return "index";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
    @LoggerManage(description="登陆后首页")
	public String home(Model model){
		List<Party> partyList = partyRepository.findAll();
		model.addAttribute("user", getUser());
		model.addAttribute("partyList",partyList);
		return "home";
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	@LoggerManage(description="登陆页面")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	@LoggerManage(description="注册页面")
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	@LoggerManage(description="主页")
	public String homeDirect(Model model) {
		User user = super.getUser();
		List<Party> partyList = partyRepository.findAll();
		if(null!=user){
			model.addAttribute("user", user);
		}
		model.addAttribute("partyList",partyList);
		return "home";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@LoggerManage(description="退出登陆")
	public String logout(HttpServletResponse response,Model model) {
		getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		getSession().removeAttribute(Const.LAST_REFERER);
		Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY,"");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "index";
	}
	
	
	@RequestMapping(value="/forgotPassword",method=RequestMethod.GET)
	@LoggerManage(description="忘记密码页面")
	public String forgotPassword() {
		return "user/forgotpassword";
	}
	
	@RequestMapping(value="/newPassword",method=RequestMethod.GET)
	@LoggerManage(description="设置新密码页面")
	public String newPassword(String email) {
		return "user/newpassword";
	}
	
	@RequestMapping(value="/uploadHeadPortrait")
	@LoggerManage(description="上传你头像页面")
	public String uploadHeadPortrait(){
		return "user/uploadheadportrait";
	}
	
	@RequestMapping(value="/uploadBackground")
	@LoggerManage(description="上传背景页面")
	public String uploadBackground(){
		return "user/uploadbackground";
	}
}
