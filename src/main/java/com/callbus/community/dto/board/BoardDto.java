package com.callbus.community.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 게시판 dto
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String subject;

    @NotNull
    @Size(min = 3, max = 500)
    private String contents;

    private Boolean isUse;

    private Long memberId;

    private String createdBy;

    private LocalDateTime createdDate;

    @QueryProjection
    public BoardDto(Long id, @Size(min = 3, max = 50) String subject, @Size(min = 3, max = 500) String contents,
                    String createdBy, LocalDateTime createdDate, Long memberId, Boolean isUse) {
        this.id = id;
        this.subject = subject;
        this.contents = contents;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.memberId = memberId;
        this.isUse = isUse;
    }
}
