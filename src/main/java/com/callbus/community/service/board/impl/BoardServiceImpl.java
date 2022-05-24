package com.callbus.community.service.board.impl;

import com.callbus.community.dto.BoardDto;
import com.callbus.community.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Override
    public List<BoardDto> getBoardList() {
        return null;
    }
}
