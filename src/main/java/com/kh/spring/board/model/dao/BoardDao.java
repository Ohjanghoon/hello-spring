package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;

@Mapper
public interface BoardDao {

	@Select("select b.*, (select count(*) from attachment where board_no = b.no) attach_count from board b order by no desc")
	List<Board> selectBoardList(RowBounds rowBounds);

	@Select("select count(*) from board")
	int getTotalContent();

	@Insert("insert into board values(seq_board_no.nextval, #{title}, #{memberId}, #{content}, default, default, default)")
	@SelectKey(statement = "select seq_board_no.currval from dual", before = false, keyProperty = "no", resultType = int.class)
	//하나의 요청에서 2개의 쿼리를 실행
	//board 객체의 no property에 (setNo(no)으로) 저장해줌
	int insertBoard(Board board);

	@Insert("insert into attachment values(seq_attachment_no.nextval, #{boardNo}, #{originalFilename}, #{renamedFilename}, default, default)")
	int insertAttachment(Attachment attach);

	@Select("select * from board where no = #{no}")
	Board selectOneBoard(int no);

	@Select("select * from attachment where board_no = #{boardNo}")
	List<Attachment> selectAttachmentListByBoardNo(int boardNo);

	Board selectOneBoardCollection(int no);

	@Select("select * from attachment where no = #{no}")
	Attachment selectOneAttachment(int no);

	@Delete("delete from attachment where no = #{attachNo}")
	int deleteAttachment(int attachNo);
	
	@Update("update board set title = #{title}, content = #{content}, updated_at = sysdate where no = #{no}")
	int updateBoard(Board board);

}
