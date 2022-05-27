package com.callbus.community.service.board;

import com.callbus.community.domain.Board;
import com.callbus.community.dto.board.BoardDto;
import com.callbus.community.dto.board.BoardUpdateForm;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 게시판 관련 인터페이스
 */
public interface BoardService {

    /**
     * 게시판 목록 조회(페이징)
     *
     * @param boardSearchForm
     * @param pageable
     * @param memberId
     * @return Page<BoardListDto>
     * @throws Exception
     */
    public Page<BoardListDto> getBoardList(BoardSearchForm boardSearchForm, Pageable pageable, Long memberId) throws Exception;

    /**
     * 게시판 등록
     * @param boardDto
     * @throws Exception
     */
    public Board insertBoard(BoardDto boardDto) throws Exception;

    /**
     * 게시판 수정
     * @param boardForm
     * @throws Exception
     */
    public Board updateBoard(BoardUpdateForm boardForm) throws Exception;

    /**
     * 게시판 삭제
     *
     * @param boardForm
     * @throws Exception
     */
    public Board deleteBoard(BoardUpdateForm boardForm) throws Exception;

}
