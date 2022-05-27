package com.callbus.community.dto.board;

import com.callbus.community.domain.AccountType;
import com.querydsl.core.annotations.QueryProjection;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * 게시판 목록 용 dto
 */
@Getter
@Setter
@ToString
public class BoardListDto {

    private Long id;

    private String subject;

    private String contents;

    private String accountType;

    private String nickName;

    private Long likeCnt;

    private String iLike;

    @QueryProjection
    public BoardListDto(Long id, String subject, String contents, String accountType, String nickName, Long likeCnt, String iLike) {
        super();
        this.id = id;
        this.subject = subject;
        this.contents = contents;
        this.accountType = accountType;
        this.nickName = nickName;
        this.likeCnt = likeCnt;
        this.iLike = iLike;
    }
}
