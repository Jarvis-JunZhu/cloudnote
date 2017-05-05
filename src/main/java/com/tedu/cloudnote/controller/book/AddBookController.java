package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/book")
public class AddBookController {
	@Resource
	private BookService bookService;
	
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute(String userId,String bookName){
		return bookService.addBook(userId, bookName);
	}
}
