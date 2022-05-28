package com.callbus.community.repository.heart;

import com.callbus.community.Exception.customException.DuplicateHeartException;
import com.callbus.community.Exception.customException.NotFoundException;
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

import java.util.*;

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

    private Map<String, Object> createMember() {
        Map<String, Object> map = new HashMap<>();
        List<Board> boards = new ArrayList<>();
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

            Board save = boardRepository.save(board);
            boards.add(save);
        }
        map.put("member", savedMember);
        map.put("boards", boards);

        return map;
    }

    @Test
    @DisplayName("좋아요 DB에 저장")
    @Transactional
    void insertHeart() throws Exception {
        //given
        Map<String, Object> returnMap = createMember();
        Member member = (Member) returnMap.get("member");

        List<Board> boards = (List<Board>) returnMap.get("boards");

        Board board = boardRepository.findById(boards.get(0).getId()).orElseThrow(() -> new Exception("조회 하신 결과가 없습니다."));

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
        Map<String, Object> returnMap = createMember();
        Member member = (Member) returnMap.get("member");
        List<Board> boards = (List<Board>) returnMap.get("boards");

        Board board = boardRepository.findById(boards.get(0).getId()).orElseThrow(() -> new NotFoundException("조회 하신 결과가 없습니다."));

        //when
        Heart hear = Heart.builder()
                .member(member)
                .board(board)
                .build();

        Heart save = heartRepository.save(hear);

        //해당 게시글에 좋아요 있는지 확인
        Heart findHeart = heartRepository.findByMemberIdAndBoardId(member.getId(), board.getId()).orElse(null);

        //then :
        assertThrows(RuntimeException.class, () -> {
            throwException(findHeart);
        });

    }

    private void throwException(Heart findHeart) throws DuplicateHeartException {
        if(findHeart != null) {
            throw new DuplicateHeartException("좋아요를 이미 하셨습니다.");
        }
    }

}