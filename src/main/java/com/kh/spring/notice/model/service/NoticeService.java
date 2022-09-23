package com.kh.spring.notice.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.ws.payload.Payload;

@Service
public class NoticeService {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	public void sendNotice(Board board) {
		Payload payload = Payload.builder()
								.to(board.getMemberId())  //글쓴이
								.msg("[" + board.getTitle() + "] 게시글을 누군가 조회했어요!")
								.time(System.currentTimeMillis())
								.build();
		
		simpMessagingTemplate.convertAndSend("/app/notice/" + board.getMemberId(), payload);
	}
	
}
