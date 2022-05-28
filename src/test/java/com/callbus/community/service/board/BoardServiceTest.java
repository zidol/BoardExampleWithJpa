package com.callbus.community.service.board;

import com.callbus.community.Exception.customException.NotFoundException;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.dto.board.BoardUpdateForm;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Member findMember = memberRepository.findById(1L).orElse(null);

        for (int i = 0; i < 12; i++) {
            //when
            Board board = Board.builder()
                    .subject("제목 테스트" + i)
                    .contents("내용 테스트" + i)
                    .member(findMember)
                    .build();

            Board saveBoard = boardRepository.save(board);

            //then
            assertThat(saveBoard.getId()).isEqualTo(board.getId());
        }
    }

    public Board createBoard() {
        Member findMember = memberRepository.findById(1L).orElseThrow(() -> new NotFoundException("없음"));

        Board board = Board.builder()
                .subject("제목 테스트")
                .contents("내용 테스트")
                .member(findMember)
                .build();

        return boardRepository.save(board);
    }

    @Test
    @DisplayName("게시판 조회 ")
    void readBoard() {
        //given
        Board board = createBoard();

        //when
        Optional<Board> findBoard = boardRepository.findById(1L);

        //then
        findBoard.ifPresent(selectedBoard -> {
            assertThat(selectedBoard.getId()).isEqualTo(1L);
        });

    }

    @Test
    @DisplayName("게사판 수정 테스트")
    @Transactional
    void editBoard() {
        //given
        Board board = createBoard();

        //when
        BoardUpdateForm boardForm = new BoardUpdateForm("제목 수정", "내용 수정", true);

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

    @Test
    @DisplayName("게사판 삭제 테스트")
    @Transactional
    void deleteBoardTest() {
        //given
        Board board = createBoard();

        //when
        BoardUpdateForm boardForm = new BoardUpdateForm();
        boardForm.setIsUse(false);

        boardRepository.findById(board.getId()).ifPresent(
                findboard -> {
                    findboard.deleteBoard(boardForm);
                }
        );


        //then
        boardRepository.findById(board.getId()).ifPresent(
                deletedBoard -> {
                    assertThat(deletedBoard.getIsUse()).isEqualTo(false);
                }
        );
    }
}