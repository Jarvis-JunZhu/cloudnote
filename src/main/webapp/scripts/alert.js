/**alert.js 封装对话框处理**/
//弹出转移笔记对话框
function alertMoveNoteWindow(){
	//加载灰色背景
	$(".opacity_bg").show();
	$("#can").load("alert/alert_move.html",function(){
		//获取book_ul中所有li
		var $li = $("#book_ul li");
		//console.log($li);
		//循环li，获取笔记本id和名称
		for(var i=0;i<$li.length;i++){
			var bookId = $($li[i]).data("bookId");
			var bookName = $li[i].innerText.trim();
			//利用笔记id和名称生成<option>
			//将<option>添加到对话框的<select>中
			$("#moveSelect").append("<option value='"+bookId+"'>- "+bookName+" -</option>");
		}
	});
};
//弹出删除笔记对话框
function alertDeleteNoteWindow(){
	//弹出对话框alert_rename.html
	$("#can").load("alert/alert_delete_note.html");
	$(".opacity_bg").show();
};
//弹出创建笔记对话框
function alertAddNoteWindow(){
	//如果没有选中的笔记本，提示
	var $a = $("#book_ul a.checked")
	if($a.length==0){
		alert("请选择笔记本");
	}else{
		//弹出对话框alert_note.html
		$("#can").load("alert/alert_note.html");
		$(".opacity_bg").show();
	}
};
//弹出重命名笔记本对话框
function alertRenameBookWindow(){
	//弹出对话框alert_rename.html
	$("#can").load("alert/alert_rename.html");
	$(".opacity_bg").show();
};
//弹出创建笔记本对话框
function alertAddBookWindow(){
	//弹出对话框alert_notebook.html
	$("#can").load("alert/alert_notebook.html");
	$(".opacity_bg").show();
};
//关闭对话框
function closeAlertWindow(){
	$("#can").empty();//清空对话框内容
	$(".opacity_bg").hide();//隐藏背景div
}
