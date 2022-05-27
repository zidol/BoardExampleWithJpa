package com.callbus.community.Exception.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 삭제, 수정 시 본인이 아닐때 Exception
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class NotAllowedUserException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NotAllowedUserException(String message) {
        super(message);
    }
}
