package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/note")
public class SearchShareNoteController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/search_share.do")
	@ResponseBody
	public NoteResult execute(String keyWord,int page){
		return noteService.searchShareNote(keyWord,page);
	}
}
