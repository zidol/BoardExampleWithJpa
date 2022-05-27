package com.callbus.community.repository.heart;

import com.callbus.community.domain.AccountType;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Heart;
import com.callbus.community.domain.Member;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class HeartRepositoryTest {

    @Autowired
    HeartRepository heartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    private Member createMember() {
        Member newMember =
                Member.builder()
                        .nickname("Kane")
                        .accountType(AccountType.LESSEE)
                        .accountId("test1")
                        .build();

        Member savedMember = memberRepository.save(newMember);

        for (int i = 1; i < 12; i++) {
            //when
            Board board = Board.builder()
                    .subject("제목 테스트" + i)
                    .contents("내용 테스트" + i)
                    .member(savedMember)
                    .build();

            boardRepository.save(board);
        }

        return savedMember;
    }

    @Test
    @DisplayName("좋아요 DB에 저장")
    @Transactional
    void insertHeart() throws Exception {
        //given
        Member member = createMember();

        Board board = boardRepository.findById(1L).orElseThrow(() -> new Exception("조회 하신 결과가 없습니다."));

        //when
        Heart hear = Heart.builder()
                .member(member)
                .board(board)
                .build();

        Heart save = heartRepository.save(hear);

        //then
        assertThat(save.getBoard().getId()).isEqualTo(board.getId());
        assertThat(save.getMember().getId()).isEqualTo(member.getId());

    }

    @Test
    @DisplayName("좋아요 DB에 저장 두번 x")
    @Transactional
    void invalidHeart() throws Exception {
        //given
        Member member = createMember();

        Board board = boardRepository.findById(1L).orElseThrow(() -> new Exception("조회 하신 결과가 없습니다."));

        //when
        Heart hear = Heart.builder()
                .member(member)
                .board(board)
                .build();

        Heart save = heartRepository.save(hear);

        Heart findHeart = heartRepository.findByMemberIdAndBoardId(member.getId(), board.getId()).orElse(null);

        //then
        assertThrows(RuntimeException.class, () -> {
            throwException(findHeart);
        });

    }

    private void throwException(Heart findHeart) throws Exception {
        if(findHeart != null) {
            throw new RuntimeException("좋아요를 이미 하셨습니다.");
        }
    }

}