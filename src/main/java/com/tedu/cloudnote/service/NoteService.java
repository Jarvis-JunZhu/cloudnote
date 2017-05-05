package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	//批量删除
	
	//组合查询
	public NoteResult searchNotes(String title,String status,String beginStr,String endStr);
	//查看分享笔记信息
	public NoteResult checkShare(String shareId);
	//搜索分享笔记
	public NoteResult searchShareNote(String keyWord,int page);
	//分享笔记
	public NoteResult shareNote(String noteId);
	//笔记移动
	public NoteResult moveNote(String bookId,String noteId);
	//删除笔记
	public NoteResult deleteNote(String noteId);
	//新增笔记
	public NoteResult addNote(String userId,String noteTitle,String bookId);
	//加载笔记列表
	public NoteResult loadBookNotes(String bookId);
	//加载笔记
	public NoteResult loadNote(String noteId);
	//保存笔记（更新笔记）
	public NoteResult updateNote(String noteTitle,String noteBody,String noteId);
}
