package com.example.IAMserver.user;

import com.example.IAMserver.exception.ApiException;
import com.example.IAMserver.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ApiException {

    private final ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;

    public BadCredentialsException(String message) {
        super(message);
    }

    @Override
    protected String getErrorCode() {
        return errorCode.getCode();
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return errorCode.getStatus();
    }
}
