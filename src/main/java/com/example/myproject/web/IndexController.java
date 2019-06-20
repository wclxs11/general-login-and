package com.example.myproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.myproject.comm.aop.LoggerManage;
import com.example.myproject.domain.User;


//页面索引跳转控制器
@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
   	
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
	public String home() {
		return "home";
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
