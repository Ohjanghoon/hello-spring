package com.kh.spring.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.kh.spring.chat.model.dto.ChatLog;
import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.ws.payload.Payload;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StompController {

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	ChatService chatService;
	
	/**
	 * @MessageMapping 작성시 주의사항
	 *  - url은 prefix를 제외하고 작성해야 한다. ex) /app/a => /a
	 * 
	 * @SendTo
	 *  - prefix부터 모두 작성
	 *  - simpleBroker에게 전달
	 */
	@MessageMapping("/a")
	@SendTo("/app/a")
	public String simpleMessage(String message) {
		log.debug("message = {}", message);
		return message;
	}
	
	@MessageMapping("/admin/notice")
	@SendTo("/app/notice")
	public Payload notice(@RequestBody Payload payload) {
		log.debug("payload = {}", payload);
		//noticeService.insertNotice(payload);  //db저장
		return payload;
	}
	
	@MessageMapping("/admin/notice/{memberId}")
	@SendTo("/app/notice/{memberId}")
	public Payload notice(@RequestBody Payload payload, @DestinationVariable String memberId) {
		log.debug("payload = {}", payload);
		log.debug("memberId = {}", memberId);
		return payload;
	}
	
	@MessageMapping("/chat/{chatroomId}")
	@SendTo({"/app/chat/{chatroomId}", "/app/admin/chatList"})
	public ChatLog chatlog(@RequestBody ChatLog chatLog) {
		log.debug("chatLog = {}", chatLog);
		
		int result = chatService.insertChatLog(chatLog);
		return chatLog;
	}
	
}
