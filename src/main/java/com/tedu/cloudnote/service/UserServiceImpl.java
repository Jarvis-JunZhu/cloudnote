package com.tedu.cloudnote.service;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;
//扫描
@Service("userService")
public class UserServiceImpl implements UserService {
	//注入
	@Resource(name="userDao")
	private UserDao userDao;
	
	/*
	 * 登录业务
	 */
	public NoteResult checkLogin(String name, String password) {
		User user = userDao.findByName(name);
		NoteResult result = new NoteResult();
		if(user==null){
			//用户名不存在
			result.setStatus(1);
			result.setMsg("用户名不存在");
			return result;
		}
		//将用户输入的明文加密
		try {
			String md5_pwd = NoteUtil.md5(password);
			if(!user.getCn_user_password().equals(md5_pwd)){
				//密码不正确
				result.setStatus(2);
				result.setMsg("密码错误");
				return result;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new NoteException("密码加密出错", e);
		}
		
		//登录成功
		result.setStatus(0);
		result.setMsg("登录成功");
		user.setCn_user_password("");//屏蔽掉密码，不返回
		result.setData(user);//返回user信息
		return result;
	}
	
	/*
	 * 注册业务
	 */
	@Transactional
	public NoteResult addUser(String name, String nick, String password) {
		NoteResult result = new NoteResult();
		try{
			//检测是否重名
			User has_user = userDao.findByName(name);
			if(has_user != null){
				result.setStatus(1);
				result.setMsg("用户名已被占用");
				return result;
			}
			//执行用户注册
			User user = new User();
			user.setCn_user_name(name);//设置用户名
			user.setCn_user_nick(nick);//设置昵称
			String md5_pwd = NoteUtil.md5(password);
			user.setCn_user_password(md5_pwd);//设置密码
			user.setCn_user_id(NoteUtil.createId());//设置用户ID
			userDao.save(user);
			//返回结果
			result.setStatus(0);
			result.setMsg("注册成功");
			//模拟异常
			String s = null;
			s.length();//抛出一个空指针异常
			return result;
		}catch(Exception e){
			throw new NoteException("用户注册异常",e);
		}
	}

}
