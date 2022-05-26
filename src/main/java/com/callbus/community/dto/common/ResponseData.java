package com.callbus.community.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
    private StatusEnum status;
    private String message;
    private Object data;

    public ResponseData() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}
