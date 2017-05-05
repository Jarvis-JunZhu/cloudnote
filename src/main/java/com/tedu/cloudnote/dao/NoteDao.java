package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Note;


public interface NoteDao {
	//批量删除
	public int batchDelete(String[] ids);
	//动态更新
	public int dynamicUpdate(Note note);
	//组合查询
	public List<Note> findNotes(Map params);
	//public void updateShareNote(String noteId);
	//public int updateBookId(Map<String,String> map);
	//public void updateStatus(String noteId);
	public int save(Note note);
	public List<Map> findByBookId(String bookId);
	public Note findById(String noteId);
	public String findTypeById(String noteId);
	//public int updateNote(Note note);
}
