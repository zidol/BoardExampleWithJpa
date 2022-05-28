package com.callbus.community.repository.board;

import com.callbus.community.Exception.customException.NotFoundException;
import com.callbus.community.domain.*;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.QBoardListDto;
import com.callbus.community.repository.heart.HeartRepository;
import com.callbus.community.repository.member.MemberRepository;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.callbus.community.domain.QBoard.board;
import static com.callbus.community.domain.QHeart.heart;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardRepositoryImplTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HeartRepository heartRepository;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    public Map<String, Object> createMember() {
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
    @Transactional
    @DisplayName("게시판글에 좋아요 등록 후 게시판 목록 조회")
    void getBoardList() {

        QHeart heartSub = new QHeart("heartSub");

        //given
        Map<String, Object> returnMap = createMember();
        Member member = (Member) returnMap.get("member");
        List<Board> boards = (List<Board>) returnMap.get("boards");

        Board board1 = boardRepository.findById(boards.get(0).getId()).orElse(null);

        //when
        //member, member2 가 board1 게시글에 좋아요 등록
        Heart newHeart = Heart.builder()
                .member(member)
                .board(board1)
                .build();
        heartRepository.save(newHeart);

        Member member2 = memberRepository.findById(2L).orElse(null);

        Heart newHeart2 = Heart.builder()
                .member(member2)
                .board(board1)
                .build();
        heartRepository.save(newHeart2);


        //then member1 게시판 목록 조회
        List<BoardListDto> fetch = queryFactory
                .select(new QBoardListDto(
                        board.id,
                        board.subject,
                        board.contents,
                        board.member.accountType
                                .when(AccountType.LESSEE).then(AccountType.LESSEE.getDesc()).
                                when(AccountType.LESSOR).then(AccountType.LESSOR.getDesc())
                                .otherwise(AccountType.REALTOR.getDesc()),
                        board.member.nickname,
                        JPAExpressions.select(heartSub.count()).from(heartSub).where(heartSub.board.id.eq(board.id)),
                        new CaseBuilder().when(heart.id.isNull()).then("N").otherwise("Y")
//                        heart.id.when(Expressions.nullExpression()).then("N").otherwise("Y")
                ))
                .from(board)
                .leftJoin(board.hearts, heart).on(heart.member.id.eq(member.getId()))
                .orderBy(board.id.asc())
                .offset(0)
                .limit(10)
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.hearts, heart).on(heart.member.id.eq(member.getId())).fetchOne();


        //첫번째 요소
        BoardListDto boardListDto = fetch.get(0);

        assertThat(boardListDto.getILike()).isEqualTo("Y");//member1이 조회 할때 좋아요 했는지 안했는지 확인
        assertThat(boardListDto.getLikeCnt()).isEqualTo(2);//좋아요 2개인지 확인
        assertThat(count).isEqualTo(11L);// 총 게시판 갯수 12개인지(페이징)
    }
}