package com.callbus.community.service.board.impl;

import com.callbus.community.dto.board.BoardForm;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Page<BoardListDto> getBoardList(BoardSearchForm boardSearchForm, Pageable pageable, Long memberId) {
        return boardRepository.getBoardList(boardSearchForm, pageable, memberId);
    }
}
