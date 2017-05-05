package com.tedu.cloudnote.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.NoteDao;
import com.tedu.cloudnote.dao.ShareDao;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.Share;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;
@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteDao noteDao;
	@Resource
	private ShareDao shareDao;
	
	//加载笔记列表
	public NoteResult loadBookNotes(String bookId) {
		NoteResult result = new NoteResult();
		List<Map> list = noteDao.findByBookId(bookId);
		result.setStatus(0);
		result.setMsg("查询完毕");
		result.setData(list);
		return result;
	}
	
	//加载笔记
	public NoteResult loadNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("加载完毕");
		result.setData(note);
		return result;
	}
	
	//保存笔记
	public NoteResult updateNote(String noteTitle, String noteBody, String noteId) {
		NoteResult result = new NoteResult();
		Note note = new Note();
		note.setCn_note_title(noteTitle);
		note.setCn_note_body(noteBody);
		note.setCn_note_id(noteId);
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		//查询一下笔记的分享状态，写入结果返回
		String typeId = noteDao.findTypeById(noteId);
		int n = noteDao.dynamicUpdate(note);
		//写返回结果
		if(n>0){
			result.setStatus(0);
			result.setMsg("保存成功");
			result.setData(typeId);
		}else{
			result.setStatus(1);
			result.setMsg("保存失败");
		}
		return result;
	}
	
	//根据bookID新建笔记
	public NoteResult addNote(String userId, String noteTitle, String bookId) {
		NoteResult result = new NoteResult();
		Note note = new Note();
		note.setCn_user_id(userId);
		note.setCn_note_title(noteTitle);
		note.setCn_notebook_id(bookId);
		note.setCn_note_create_time(System.currentTimeMillis());
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		note.setCn_note_id(NoteUtil.createId());
		int n = noteDao.save(note);
		if(n>0){
			result.setStatus(0);
			result.setMsg("新建笔记成功");
			result.setData(note.getCn_note_id());
		}else{
			result.setStatus(1);
			result.setMsg("新建笔记失败");
		}
		return result;
	}
	
	//根据笔记id删除笔记
	public NoteResult deleteNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_status_id("2");
		noteDao.dynamicUpdate(note);
		result.setStatus(0);
		result.setMsg("删除成功");
		return result;
	}
	
	//转移笔记到另一个笔记本
	public NoteResult moveNote(String bookId, String noteId) {
		NoteResult result = new NoteResult();
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		int n = noteDao.dynamicUpdate(note);
		//int n = noteDao.updateBookId(map);
		if(n>0){
			result.setStatus(0);
			result.setMsg("转移笔记成功");
		}else{
			result.setStatus(1);
			result.setMsg("转移笔记失败");
		}
		return result;
	}
	
	//分享笔记
	public NoteResult shareNote(String noteId) {
		NoteResult result = new NoteResult();
		//根据笔记id从笔记表中查出笔记
		Note note = noteDao.findById(noteId);
		//判断该笔记是否分享过，如果typeId为2则分享过，其它为没有分享过
		if("2".equals(note.getCn_note_type_id())){
			//分享过
			result.setStatus(2);
			result.setMsg("该笔记已经分享过了");
		}else{
			//未分享过
			result.setStatus(0);
			result.setMsg("笔记分享成功");
			//新建分享笔记插入分享表
			Share share = new Share();
			share.setCn_note_id(noteId);
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_id(NoteUtil.createId());
			share.setCn_share_title(note.getCn_note_title());
			shareDao.save(share);
			//修改原笔记表中的笔记分享typeid
			Note note1 = new Note();
			note1.setCn_note_id(noteId);
			note1.setCn_note_type_id("2");
			noteDao.dynamicUpdate(note1);
			//noteDao.updateShareNote(noteId);
		}
		return result;
	}

	//搜索分享笔记
	public NoteResult searchShareNote(String keyWord,int page) {
		NoteResult result = new NoteResult();
		String title = "%";
		if(keyWord!=null && !"".equals(keyWord)){
			title = "%" + keyWord + "%";
		}
		//构建map参数
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("keyWord", title);
		//计算抓取点
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*5;
		params.put("begin", begin);
		List<Share> shares = shareDao.findLikeTitle(params);
		result.setStatus(0);
		result.setMsg("搜索完毕");
		result.setData(shares);
		return result;
	}

	//查看分享笔记信息
	public NoteResult checkShare(String shareId) {
		NoteResult result = new NoteResult();
		Share share = shareDao.findByShareId(shareId);
		result.setStatus(0);
		result.setMsg("加载笔记信息成功");
		result.setData(share);
		return result;
	}

	public NoteResult searchNotes(String title, String status, String beginStr, String endStr) {
		NoteResult result = new NoteResult();
		Map<String,Object> params = new HashMap<String,Object>();
		//标题
		if(title!=null && !"".equals(title)){
			params.put("title", "%"+title+"%");
		}
		//状态，如果不是全部选项“0”
		if(!"0".equals(status)){
			params.put("status", status);
		}
		//开始日期
		if(beginStr!=null && !"".equals(beginStr)){
			Date beginDate = Date.valueOf(beginStr);
			params.put("begin", beginDate.getTime());
		}
		//结束日期
		if(endStr!=null && !"".equals(endStr)){
			Date endDate = Date.valueOf(endStr);
			params.put("end", endDate.getTime());
		}
		List<Note> list = noteDao.findNotes(params);
		result.setStatus(0);
		result.setMsg("搜索完毕");
		result.setData(list);
		return result;
	}

}
