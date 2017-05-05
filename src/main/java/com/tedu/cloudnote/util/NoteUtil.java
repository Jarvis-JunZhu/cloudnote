package com.tedu.cloudnote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;


public class NoteUtil {
	/**
	 * 将传入的str加密处理
	 * @param str 明文字符串
	 * @return 加密后的字符串结果
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5(String str) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] output = md.digest(str.getBytes());
		//将MD5处理结果利用Base64转成字符串
		return Base64.encodeBase64String(output);
	}
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	public static void main(String[] args) throws NoSuchAlgorithmException{
		System.out.println(md5("123456"));
		System.out.println(md5("admin"));
		System.out.println(createId());
	}
}
