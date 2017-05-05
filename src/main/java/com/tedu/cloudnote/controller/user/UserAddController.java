package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/user")
public class UserAddController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute(String name,String nick,String password){
		System.out.println("111");
		return userService.addUser(name, nick, password);
	}
}
