package com.callbus.community.repository.board;

import com.callbus.community.domain.AccountType;
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
import org.springframework.util.StringUtils;

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
                .leftJoin(board.hearts, heart).on(memberId != null ? heart.member.id.eq(memberId) : heart.isNull())
                .where(
                        board.isUse.eq(true),
                        titleEq(boardSearchForm.getSubject()),
                        contentEq(boardSearchForm.getContents())
                )
                .orderBy(board.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.hearts, heart).on(memberId != null ? heart.member.id.eq(memberId) : heart.isNull())
                .where(
                        board.isUse.eq(true),
                        titleEq(boardSearchForm.getSubject()),
                        contentEq(boardSearchForm.getContents())

                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);//() -> countQuery.fetchOne()
    }
    //제목 검색
    private BooleanExpression titleEq(String subject) {
        return StringUtils.hasText(subject) ? board.subject.contains(subject) : null;
    }

    //내용 검색
    private BooleanExpression contentEq(String content) {
        return StringUtils.hasText(content) ? board.contents.contains(content) : null;
    }
}
