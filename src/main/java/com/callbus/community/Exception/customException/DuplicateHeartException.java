package com.callbus.community.Exception.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 좋아요 중복시 Exception
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class DuplicateHeartException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateHeartException(String message) {
        super(message);
    }
}
