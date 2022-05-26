package com.callbus.community.repository.board;

import com.callbus.community.domain.QHeart;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.dto.board.QBoardListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.callbus.community.domain.QBoard.board;
import static com.callbus.community.domain.QHeart.heart;

/**
 * 게시판 QueryDSL 구현부
 */
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardListDto> getBoardList(BoardSearchForm boardSearchForm, Pageable pageable, Long memberId) {
        QHeart heartSub = new QHeart("heartSub");

        List<BoardListDto> content = queryFactory
                .select(new QBoardListDto(
                        board.id,
                        board.subject,
                        board.contents,
                        JPAExpressions.select(heartSub.count()).from(heartSub).where(heartSub.board.id.eq(board.id)),
                        new CaseBuilder().when(heart.id.isNull()).then("N").otherwise("Y")
//                        heart.id.when(Expressions.nullExpression()).then("N").otherwise("Y")
                ))
                .from(board)
                .leftJoin(board.hearts, heart).on(memberId != null ? heart.member.id.eq(memberId) : heart.isNull())
                .orderBy(board.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.hearts, heart).on(memberId != null ? heart.member.id.eq(memberId) : heart.isNull());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);//() -> countQuery.fetchOne()
    }
}
