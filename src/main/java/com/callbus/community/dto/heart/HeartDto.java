package com.callbus.community.dto.heart;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class HeartDto {

    private Long id;
    private Long memberId;
    @NotNull
    private Long boardId;
}
