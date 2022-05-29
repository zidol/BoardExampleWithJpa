package com.callbus.community.dto.board;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchForm {
    @ApiModelProperty(value = "제목")
    private String subject;
    @ApiModelProperty(value = "내용")
    private String contents;
}
