package com.example.IAMserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", HttpStatus.CONFLICT),
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND),
    INVALID_REQUEST("INVALID_REQUEST", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final HttpStatus status;

    ErrorCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

}
