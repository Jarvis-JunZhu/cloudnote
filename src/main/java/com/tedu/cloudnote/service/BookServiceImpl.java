package com.tedu.cloudnote.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.BookDao;
import com.tedu.cloudnote.entity.Book;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;
@Service("bookService")
public class BookServiceImpl implements BookService {

	@Resource
	private BookDao bookDao;
	
	public NoteResult loadUserBooks(String userId) {
		NoteResult result = new NoteResult();
		List<Book> list = bookDao.findByUserId(userId);
		result.setStatus(0);
		result.setMsg("加载笔记本列表成功");
		result.setData(list);
		return result;
	}

	public NoteResult addBook(String userId, String bookName) {
		NoteResult result = new NoteResult();
		Book book = new Book();
		book.setCn_notebook_id(NoteUtil.createId());
		book.setCn_user_id(userId);
		book.setCn_notebook_name(bookName);
		book.setCn_notebook_type_id("5");
		book.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		int n = bookDao.save(book);
		if(n>0){
			result.setStatus(0);
			result.setMsg("新建笔记本成功");
			result.setData(book);
		}else{
			result.setStatus(1);
			result.setMsg("新建笔记本失败");
		}
		return result;
	}

	public NoteResult updateBook(String bookId, String bookName) {
		NoteResult result = new NoteResult();
		Map<String,String> map = new HashMap<String,String>();
		map.put("cn_notebook_id", bookId);
		map.put("cn_notebook_name", bookName);
		int n = bookDao.updateBookName(map);
		if(n>0){
			result.setStatus(0);
			result.setMsg("重命名成功");
		}else{
			result.setStatus(1);
			result.setMsg("重命名失败");
		}
		return result;
	}

}









