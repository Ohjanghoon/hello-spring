package com.kh.spring.chat.model.dto;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatMember {

	@NonNull
	private String chatroomId;
	@NonNull
	private String memberId;
	private long lastCheck;
	private LocalDateTime createdAt;
	private LocalDateTime deletedAt;
	
}
