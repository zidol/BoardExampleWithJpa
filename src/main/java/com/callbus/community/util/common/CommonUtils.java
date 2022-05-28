package com.callbus.community.util.common;

import com.callbus.community.dto.common.ResponseData;
import com.callbus.community.dto.common.StatusEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CommonUtils {

    public static ResponseEntity<Object> setObjectResponseEntity(Object resultData, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        ResponseData data = new ResponseData();
        data.setStatus(StatusEnum.OK);
        data.setMessage(message);
        data.setData(resultData);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
