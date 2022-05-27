package com.callbus.community.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    //상태 코드
    int status;

    //상태 코드 명
    String error;

    //오류 메시지
    String message;

    //요청 url
    String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime timestamp;

    List<Error> errorList;
}
