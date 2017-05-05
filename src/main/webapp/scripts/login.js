/**login.js 封装登录和注册处理**/
//主处理
$(function(){//页面载入 
	//给登录按钮绑定单击处理
	$('#login').click(checkLogin);
	//给注册按钮绑定单击处理
	$('#regist_button').click(registUser);
	//给登录按钮绑定一个回车事件
	$("#dl").keydown(function(event){
		if(event.keyCode==13){
			$('#login').click()
		}
	});
	//给注册按钮绑定一个回车事件
	$("#zc").keydown(function(event){
		if(event.keyCode==13){
			$('#regist_button').click()
		}
	});
});


//登录处理
function checkLogin(){
	//获取请求参数
	var name = $('#count').val().trim();
	var password = $('#password').val().trim();
	//清空以前的提示信息
	$('#count_span').html("");
	$('#password_span').html("");
	//检测参数格式
	var ok = true;//是否通过检测
	if(name==''){
		ok = false;
		$('#count_span').html('用户名为空');
	}
	if(password==''){
		ok = false;
		$('#password_span').html('密码为空');
	}
	//发送Ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/user/login.do",//请求地址
			type:"post",//请求类型
			data:{"name":name,"password":password},//请求数据
			dataType:"json",//服务器返回的数据类型
			success:function(result){
				if(result.status==0){
					//成功
					//将用户信息写入Cookie
					var user = result.data;//获取返回的user信息
					addCookie("uid",user.cn_user_id,2);
					addCookie("uname",user.cn_user_name,2);
					window.location.href="edit.html";
				}else if(result.status==1){
					//用户名错误
					$('#count_span').html(result.msg);
				}else if(result.status==2){
					//密码错误
					$('#password_span').html(result.msg);
				}
			},//回调函数
			error:function(){
				alert("登录异常");
			}
		});
	}
}
//注册处理
function registUser(){
	//获取请求参数
	var name = $("#regist_username").val().trim();
	var nick = $("#nickname").val().trim();
	var password = $("#regist_password").val().trim();
	var f_password = $("#final_password").val().trim();
	//清空消息
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	//格式检查
	//设置开关
	var ok = true;//是否通过验证
	if(name==""){
		$("#warning_1").show();
		$("#warning_1 span").html("用户名为空");
		ok = false;
	}
	if(password==""){
		$("#warning_2").show();
		$("#warning_2 span").html("密码为空");
		ok = false;
	}else if(password.length<6){
		$("#warning_2").show();
		$("#warning_2 span").html("密码长度太短");
		ok = false;
	}
	if(f_password==""){
		$("#warning_3").show();
		$("#warning_3 span").html("确认密码为空");
		ok = false;
	}else if(f_password != password){
		$("#warning_3").show();
		$("#warning_3 span").html("密码不一致");
		ok = false;
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/user/add.do",
			type:"post",
			data:{"name":name,"nick":nick,"password":password},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//注册成功
					alert(result.msg);
					$("#back").click();
				}else if(result.status==1){
					//用户名被占用
					$("#warning_1").show();
					$("#warning_1 span").html(result.msg);
				}
			},
			error:function(){
				alert("注册异常")
			}
		})
	}
	//alert("---");
}