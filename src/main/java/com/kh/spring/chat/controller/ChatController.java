package com.kh.spring.chat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.chat.model.dto.ChatLog;
import com.kh.spring.chat.model.dto.ChatMember;
import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.member.model.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	ChatService chatService;
	
	/**
	 * 1. 채팅방 유무 조회
	 * 
	 * 2.a. 처음 입장한 경우
	 * 		- 채팅방 아이디 생성(문자열 토큰)
	 * 2.b. 재입장인 경우
	 * 		- 기존 채팅방 아이디
	 */		
	@GetMapping("/chat.do")
	public void chat(Authentication authentication, @RequestParam("chatTargetId") String chatTargetId, Model model) {
		//1. 채팅방 유무 조회
		Member member = (Member) authentication.getPrincipal();
		String memberId = member.getMemberId();
		
		ChatMember chatMember = chatService.findChatMemberByMemberId(memberId, chatTargetId);
		log.debug("chatMember = {}", chatMember);
		// 처음 입장이면 null
		
		String chatroomId = null;
		List<ChatLog> chatLogs = new ArrayList<>();
		
		if(chatMember == null) {
			//처음 입장한 경우
			chatroomId = generateChatroomId();
			log.debug("chatroomId = {}", chatroomId);
			//chatMember insert 2행(사용자, 관리자)
			List<ChatMember> chatMembers = Arrays.asList(
						new ChatMember(chatroomId, memberId),
						new ChatMember(chatroomId, chatTargetId));
			chatService.insertChatMembers(chatMembers);
		}
		else {
			//재입장한 경우
			chatroomId = chatMember.getChatroomId();
			chatLogs = chatService.findChatLogByChatroomId(chatroomId);
			log.debug("chatLogs = {}", chatLogs);
		}
		
		model.addAttribute("chatroomId", chatroomId);
		model.addAttribute("chatLogs", chatLogs);
		
	}

	/**
	 * 대문자/소문자/숫자 조합으로 임의의 문자열 
	 */
	private String generateChatroomId() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		final int len = 8;
		for(int i = 0; i < len; i++) {
			if(random.nextBoolean()) {
				//영문자
				if(random.nextBoolean()) {
					//대문자
					sb.append((char) (random.nextInt(26) + 'A'));
				}
				else {
					//소문자
					sb.append((char) (random.nextInt(26) + 'a'));
				}
			}
			else {
				//숫자
				sb.append(random.nextInt(10));
			}
			
		}
		return sb.toString();
	}
	
	
	@GetMapping("/chatList.do")
	public void chatList(Authentication authentication, Model model) {
		Member member = (Member) authentication.getPrincipal();
		String memberId = member.getMemberId();
		
		List<ChatMember> chatMembers = chatService.findMyChat(memberId);
		
		model.addAttribute("chatMembers", chatMembers);
		
	}
}
