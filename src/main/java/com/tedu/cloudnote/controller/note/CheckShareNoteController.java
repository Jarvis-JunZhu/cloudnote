package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/note")
public class CheckShareNoteController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/check_share.do")
	@ResponseBody
	public NoteResult execute(String shareId){
		return noteService.checkShare(shareId);
	}
}
