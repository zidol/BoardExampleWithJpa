package com.callbus.community.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 게시판 조회용 form
 */
@Getter
@Setter
@NoArgsConstructor
public class BoardForm {

    private Long id;

    private String subject;

    private String contents;

    private Boolean isUse;

    public BoardForm(String subject, String contents, Boolean isUse) {
        this.subject = subject;
        this.contents = contents;
        this.isUse = isUse;
    }
}
