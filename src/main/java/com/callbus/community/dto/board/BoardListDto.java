package com.callbus.community.dto.board;

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

    private Long likeCnt;

    private String iLike;

    @QueryProjection
    public BoardListDto(Long id, String subject, String contents, Long likeCnt, String iLike) {
        super();
        this.id = id;
        this.subject = subject;
        this.contents = contents;
        this.likeCnt = likeCnt;
        this.iLike = iLike;
    }
}
