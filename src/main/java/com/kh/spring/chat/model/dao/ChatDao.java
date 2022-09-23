package com.kh.spring.chat.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.chat.model.dto.ChatLog;
import com.kh.spring.chat.model.dto.ChatMember;

public interface ChatDao {

	@Select("select * from chat_member c join chat_member t using(chatroom_id) where c.member_id = #{memberId} and t.member_id = #{chatTargetId}")
	ChatMember findChatMemberByMemberId(@Param("memberId") String memberId, @Param("chatTargetId") String chatTargetId);

	@Insert("insert into chat_member values(#{chatroomId}, #{memberId}, 0, default, default)")
	void insertChatMember(ChatMember cm);

	@Insert("insert into chat_log values(seq_chat_log_no.nextval, #{chatroomId}, #{memberId}, #{msg}, #{time})")
	int insertChatLog(ChatLog chatLog);

	@Select("select * from chat_log where chatroom_id = #{chatroomId} order by no")
	List<ChatLog> findChatLogByChatroomId(String chatroomId);

	@Select("select\r\n"
			+ "    no,\r\n"
			+ "    chatroom_id,\r\n"
			+ "    (select member_id from chat_member where chatroom_id = cl.chatroom_id and member_id != 'admin') member_id,"
			+ "    msg,\r\n"
			+ "    time\r\n"
			+ "from(\r\n"
			+ "    select\r\n"
			+ "        cl.*,\r\n"
			+ "        row_number() over(partition by chatroom_id order by no desc) rnum\r\n"
			+ "    from\r\n"
			+ "        chat_log cl) cl\r\n"
			+ "where\r\n"
			+ "    rnum = 1\r\n"
			+ "order by\r\n"
			+ "    time desc")
	List<ChatLog> findRecentChatLogs();

	@Select("select tar.* from chat_member c join chat_member tar on c.chatroom_id = tar.chatroom_id where c.member_id = #{memberId} and tar.member_id != #{memberId}")
	List<ChatMember> findMyChat(String memberId);

	
}
