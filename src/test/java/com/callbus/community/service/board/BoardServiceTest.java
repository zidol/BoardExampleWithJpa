package com.callbus.community.service.board;

import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.dto.BoardForm;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("게시판 작성 테스트")
    void saveBoard() {
        //given
        Member findMember = memberRepository.findById(1L).get();

        //when
        Board board = Board.builder()
                .subject("제목 테스트")
                .contents("내용 테스트")
                .member(findMember)
                .build();

        Board saveBoard = boardRepository.save(board);

        //then
        assertThat(saveBoard.getId()).isEqualTo(board.getId());
    }

    private Board createBoard() {
        Member findMember = memberRepository.findById(1L).get();

        Board board = Board.builder()
                .subject("제목 테스트")
                .contents("내용 테스트")
                .member(findMember)
                .build();

        return boardRepository.save(board);
    }

    @Test
    @DisplayName("게사판 수정 테스트")
    @Transactional
    void editBoard() {
        //given
        Board board = createBoard();

        System.out.println("board.getId() = " + board.getId());

        //when
        BoardForm boardForm = new BoardForm("제목 수정", "내용 수정", true);

        boardRepository.findById(board.getId()).ifPresent(
                findboard -> {
                    findboard.changeBoard(boardForm);
                }
        );


        //then
        boardRepository.findById(board.getId()).ifPresent(
                changedboard -> {
                    assertThat(changedboard.getContents()).isEqualTo(boardForm.getContents());
                    assertThat(changedboard.getSubject()).isEqualTo(boardForm.getSubject());
                }
        );
    }
}