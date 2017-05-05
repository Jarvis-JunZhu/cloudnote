package com.tedu.cloudnote.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面：封装打桩操作逻辑
 */
@Component
@Aspect//指定为切面
public class LoggerBean {
	//指定通知类型
	@Before("within(com.tedu.cloudnote.controller..*)")
	public void loggerController(){
		System.out.println("进入Controller组件处理");
	}
}
