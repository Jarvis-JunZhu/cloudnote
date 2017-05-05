/**note.js 封装笔记相关操作**/
//分页加载搜索分享笔记
function searchSharePage(keyWord,page){
	$.ajax({
		url:base_path+"/note/search_share.do",
		type:"post",
		data:{"keyWord":keyWord,"page":page},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//得到搜索结果
				var lis = result.data;
				//循环结果,生成li元素，添加进"搜索列表"
				for(var i=0;i<lis.length;i++){
					var shareId = lis[i].cn_share_id;
					var shareTitle = lis[i].cn_share_title;
					var sli = "";
					sli+='<li >';
					sli+='	<a >';
					sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
					sli+=	     shareTitle;
					sli+='	    <button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
					sli+='	</a>';
					sli+='</li>';
					//将noteId绑定到li元素上
					var $li = $(sli).data("shareId",shareId);
					//将li元素添加到笔记列表ul中
					$("#search_ul").append($li);
				}
			}
		},
		error:function(){
			alert("搜索异常");
		}
	});
};
//笔记列表区显示控制
function displayZone(id){
	if(id=="pc_part_2"){
		$("#pc_part_2").show();
	}else{
		$("#pc_part_2").hide();
	}
	if(id=="pc_part_4"){
		$("#pc_part_4").show();
	}else{
		$("#pc_part_4").hide();
	}
	if(id=="pc_part_6"){
		$("#pc_part_6").show();
	}else{
		$("#pc_part_6").hide();
	}
	if(id=="pc_part_7"){
		$("#pc_part_7").show();
	}else{
		$("#pc_part_7").hide();
	}
	if(id=="pc_part_8"){
		$("#pc_part_8").show();
	}else{
		$("#pc_part_8").hide();
	}
};
//分享笔记
function shareNote(){
	//获取请求参数：noteId
	//$(this).parents("li")
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/share.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//添加分享图标
				var img = '<i class="fa fa-sitemap"></i>';
				$li.find(".btn_slide_down").before(img);
				//提示成功
				alert(result.msg);
			}else if(result.status==2){
				//提示已分享过
				alert(result.msg);
			}
		},
		error:function(){
			alert("笔记分享异常");
		}
	});
};
//笔记转移功能
function noteMove(){
	//获取请求参数选中的笔记id，要转入的笔记本ID
	var out_bookId = $("#book_ul a.checked").parent().data("bookId");
	var in_bookId = $("#moveSelect").val();
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//清除提示信息
	$("#select_span").html("");
	//格式验证
	var ok = true;
	//alert(in_bookId);
	if(in_bookId=='none'){
		ok = false;
	}
	if(out_bookId == in_bookId){
		ok = false;
		$("#select_span").html("<font color='red'>转入笔记本与转出笔记相同</font>");
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/note/move.do",
			type:"post",
			data:{"bookId":in_bookId,"noteId":noteId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//移除笔记列表li元素
					$li.remove();
					//提示成功
					alert(result.msg);
				}
			},
			error:function(){
				alert("转移笔记异常");
			}
		});
	}
}
//删除笔记
function deleteNote(){
	//获取请求参数noteId
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//格式验证
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/delete.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//移除列表中删除的笔记li元素
				$li.remove();
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除异常");
		}
	});
}
//隐藏笔记菜单
function hideNoteMenu(){
	$("#note_ul div").hide();
}
//弹出笔记菜单
function popNoteMenu(){
	//隐藏所有笔记菜单
	$("#note_ul div").hide();
	var $menu = $(this).parent().next();
	$menu.slideDown(100);
	//设置点击笔记选中效果
	$("#note_ul a").removeClass("checked");
	$(this).parent().addClass("checked");
	//阻止事件向li，body冒泡
	return false;
}
//新建笔记业务
function createNewNote(){
	//获取请求参数userId,noteTitle,bookId
	var userId = getCookie("uid");
	var noteTitle = $("#input_note").val().trim();
	var bookId = $("#book_ul a.checked").parent().data("bookId");
	//格式验证
	var ok = true;
	if(userId==null){
		ok = false;
		window.location.href = "log_in.html";
	}
	if(noteTitle==""){
		ok = false;
		$("#input_note_span").html("<font color='red'>笔记标题不能为空</font>");
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/note/add.do",
			type:"post",
			data:{"userId":userId,"noteTitle":noteTitle,"bookId":bookId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//获取noteTitle,noteId
					var noteId = result.data;
					//关闭对话框
					closeAlertWindow();
					//创建笔记列表
					createNoteLi(noteTitle,noteId);
					//提示创建成功
					alert(result.msg);
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("新建笔记异常");
			}
		});
	}
}
//保存笔记按钮处理
function updateNote(){
	//获取请求参数
	var noteTitle = $("#input_note_title").val().trim();
	var noteBody = um.getContent();
	var $li =  $("#note_ul a.checked").parent()
	var noteId = $li.data("noteId");
	//清除上次提示信息
	$("#note_title_span").html("");
	//格式验证
	if($li.length==0){
		alert("请选择要保存的笔记");
	}else if(noteTitle==""){
		$("#note_title_span").html("<font color='red'>笔记标题不能为空</font>");
	}else{
		//发送ajax请求
		$.ajax({
			url:base_path+"/note/update.do",
			type:"post",
			data:{"noteTitle":noteTitle,"noteBody":noteBody,"noteId":noteId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//更新列表li
					var sli="";
					sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
					sli+=	    noteTitle;
					sli+='	    <button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
					$li.find("a").html(sli);
					//判断笔记分享状态，如果是分享过，要加分享图标
					if(result.data=='2'){
						var slis="";
						slis+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
						slis+=	    noteTitle;
						slis+='		<i class="fa fa-sitemap"></i>';
						slis+='	    <button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
						$li.find("a").html(slis);
					}
					//提示成功
					alert(result.msg);
				}else if(result.status==1){
					alert(result.msg);
				}
			},
			error:function(){
				alert("保存异常");
			}
		});
	 }
}
//根据笔记id加载笔记信息
function loadNote(){
	$("#pc_part_5").hide();
	$("#pc_part_3").show();
	//添加笔记选中效果
	$("#note_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var noteId = $(this).data("noteId");
	//格式验证
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/load.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var note = result.data;
				var title = note.cn_note_title;
				var body = note.cn_note_body
				$("#input_note_title").val(title);//笔记标题
				um.setContent(body);//笔记内容
			}
		},
		error:function(){
			alert("加载笔记内容异常");
		}
	});
}
//加载用户笔记列表
function loadBookNotes(){
	//显示笔记div
	displayZone('pc_part_2');
	//设置笔记本li选中效果
	$("#book_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var bookId = $(this).data("bookId");
	//格式检查
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/loadnotes.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//获取服务器返回的笔记集合信息
				var notes = result.data;
				//清空原有笔记列表元素
				//$("#note_ul").html("");
				//$("#note_ul li").remove();
				$("#note_ul").empty();
				//循环生成笔记li元素
				for(var i=0;i<notes.length;i++){
					//获取笔记ID和笔记标题
					var noteTitle = notes[i].cn_note_title;
					var noteId = notes[i].cn_note_id;
					//创建一个笔记li元素
					createNoteLi(noteTitle,noteId);
					//将新添加的元素判断是否该加分享图标
					var typeId = notes[i].cn_note_type_id;
					if(typeId=='2'){
						var img = '<i class="fa fa-sitemap"></i>';
						//获取新添加的li元素
						var $li = $("#note_ul li:last");
						$li.find(".btn_slide_down").before(img);
					}
				}
			}
		},
		error:function(){
			alert("加载笔记列表异常");
		}
	});
}
//创建一个笔记li元素
function createNoteLi(noteTitle,noteId){
	var sli = "";
	sli+='<li class="online">';
	sli+='	<a >';
	sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
	sli+=	    noteTitle;
	sli+='	    <button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='	</a>';
	sli+='	<div class="note_menu" tabindex="-1">';
	sli+='		<dl>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli+='		</dl>';
	sli+='	</div>';
	sli+='</li>';
	//将noteId绑定到li元素上
	var $li = $(sli).data("noteId",noteId);
	//将li元素添加到笔记列表ul中
	$("#note_ul").append($li);
}
