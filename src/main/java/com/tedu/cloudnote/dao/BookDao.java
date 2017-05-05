package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Book;


/**
 *
 */
public interface BookDao {
	public int updateBookName(Map map);
	public int save(Book book);
	public List<Book> findByUserId(String id);
}
