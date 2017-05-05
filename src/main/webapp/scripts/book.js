/**book.js 封装笔记本相关处理**/
//重命名笔记本名实现
function renameBookState(){
	//获取请求参数bookId,bookName
	var bookId = $("#book_ul a.checked").parent().data("bookId");
	var bookName = $("#input_notebook_rename").val().trim();
	//格式验证
	var ok = true;
	if(bookName==""){
		ok = false;
		$("#input_notebook_rename_span").html("<font color='red'>笔记本名不能为空</font>")
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/book/rename.do",
			type:"post",
			data:{"bookId":bookId,"bookName":bookName},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//关闭窗口
					closeAlertWindow();
					//更新列表
					var sli = "";
					sli+= 	'<i class="fa fa-book" title="online" rel="tooltip-bottom">';
					sli+= 	'</i> '+bookName;
					$("#book_ul a.checked").html(sli);
					//提示成功
					alert(result.msg);
				}else{
					closeAlertWindow();
					alert(result.msg);
				}
			},
			error:function(){
				alert("重命名异常");
			}
		});
	}
}
//新建笔记本 按钮实现
function createBook(){
	//获取请求数据
	var userId = getCookie("uid");
	var bookName = $("#input_notebook").val().trim();
	//验证格式
	var ok = true;
	if(userId==null){
		ok = false;
		window.location.href="log_in.html";
	}else if(bookName==""){
		ok = false;
		$("#input_notebook_span").html("<font color='red'>笔记本名不能为空</font>");
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/book/add.do",
			type:"post",
			data:{"userId":userId,"bookName":bookName},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					var bookId = result.data.cn_notebook_id;
					//关闭对话框
					closeAlertWindow();
					createBookLi(bookId,bookName);
					//提示笔记本创建成功
					alert(result.msg);
				}
			},
			error:function(){
				closeAlertWindow();
				alert("创建笔记本异常");
			}
		});
	}
}
//创建笔记本列表li元素
function createBookLi(bookId,bookName){
	//在笔记列表中添加li
	var sli = "";
	sli+= '<li class="online">';
	sli+= '<a  >';
	sli+= 	'<i class="fa fa-book" title="online" rel="tooltip-bottom">';
	sli+= 	'</i> '+bookName;
	sli+= '</a>';
	sli+= '</li>';
	//将bookId绑定到li元素上
	var $li = $(sli);
	$li.data("bookId",bookId);
	//将li元素添加到ul列表中
	$("#book_ul").append($li);
}
//加载用户笔记本列表
function loadUserBooks(){
	//获取请求参数
	var userId = getCookie("uid");
	//检查格式
	if(userId==null){
		window.location.href="log_in.html";
	}else{
		//发送ajax请求
		$.ajax({
			url:base_path+"/book/loadbooks.do",
			type:"post",
			data:{"userId":userId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//获取返回的笔记本集合
					var books = result.data;
					//循环生成列表元素
					for(var i=0;i<books.length;i++){
						//获取笔记本ID
						var bookId = books[i].cn_notebook_id;
						//获取笔记本名称
						var bookName = books[i].cn_notebook_name;
						//构建列表li元素
						createBookLi(bookId,bookName);
					}
				}
			},
			error:function(){
				alert("加载笔记本列表异常");
			}
		});
	}
}