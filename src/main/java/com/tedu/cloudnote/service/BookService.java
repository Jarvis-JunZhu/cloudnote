package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult updateBook(String bookId,String bookName);
	public NoteResult addBook(String userId,String bookName);
	public NoteResult loadUserBooks(String userId);
}
