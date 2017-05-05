package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/user")
public class UserLoginController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public NoteResult execute(String name,String password){
		//System.out.println(name+":"+password);
		return userService.checkLogin(name, password);
	}
}
