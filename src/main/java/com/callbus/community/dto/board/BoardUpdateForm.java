package com.callbus.community.dto.board;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * 게시판 조회용 form
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardUpdateForm {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String subject;

    @NotEmpty
    @Size(min = 3, max = 500)
    private String contents;

    private Boolean isUse;

    private Long memberId;

    public BoardUpdateForm(String subject, String contents, Boolean isUse) {
        this.subject = subject;
        this.contents = contents;
        this.isUse = isUse;
    }
}
