package com.callbus.community.service.board;

import com.callbus.community.dto.BoardDto;

import java.util.List;

/**
 * 게시판 관련 인터페이스
 */
public interface BoardService {

    public List<BoardDto> getBoardList();
}
