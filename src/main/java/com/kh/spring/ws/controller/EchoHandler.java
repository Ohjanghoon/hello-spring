package com.kh.spring.ws.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EchoHandler extends TextWebSocketHandler {
	
	/**
	 * Session관리를 직접 해주어야 한다.
	 */
	List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();  // 멀티쓰레딩환경에서 사용하는 리스트
			
	/**
	 * @OnOpen 와 비슷
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		log.debug("[add 현재 세션수 {}] {}", sessionList.size(), session.getId());
	}
	
	/**
	 * @OnClose 와 비슷
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		log.debug("[remove 현재 세션수 {}] {}", sessionList.size(), session.getId());
	}
	
	/**
	 * @OnMessage 와 비슷
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.debug("[message] {} : {}", session.getId(), message.getPayload());
		
		for(WebSocketSession sess : sessionList) {
			sess.sendMessage(new TextMessage(session.getId() + " : " + message.getPayload()));
		}
	}
}
