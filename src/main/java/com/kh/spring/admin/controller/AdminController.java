package com.kh.spring.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.chat.model.dto.ChatLog;
import com.kh.spring.chat.model.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	ChatService chatService;
	
	@GetMapping("/memberList.do")
	public void memberList() {
		
	}
	
	@GetMapping("/chatList.do")
	public void chatList(Model model) {
		//채팅방별로 최근 1건 조회
		List<ChatLog> list = chatService.findRecentChatLogs(); 
		log.debug("list = {}", list);
		model.addAttribute("list", list);
	}
	
	@GetMapping("/chat.do")
	public void chat(@RequestParam String chatroomId, Model model) {
		//채팅로그목록조회
		List<ChatLog> chatLogs = chatService.findChatLogByChatroomId(chatroomId);
		model.addAttribute("chatLogs", chatLogs);
	}
}
