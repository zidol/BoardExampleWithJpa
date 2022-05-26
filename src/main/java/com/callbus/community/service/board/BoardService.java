package com.callbus.community.service.board;

import com.callbus.community.dto.board.BoardForm;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 게시판 관련 인터페이스
 */
public interface BoardService {

    public Page<BoardListDto> getBoardList(BoardSearchForm boardSearchForm, Pageable pageable, Long memberId);

}
