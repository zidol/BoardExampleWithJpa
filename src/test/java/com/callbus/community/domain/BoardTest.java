package com.callbus.community.domain;

import com.callbus.community.repository.board.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 저장  테스트")
    void boardSave() {
        Board board = Board.builder()
                .subject("테스트")
                .contents("내용")
                .build();

        Board result = boardRepository.save(board);

        assertThat(board.getId()).isEqualTo(result.getId());
    }

}