package com.example.IAMserver.user;

import com.example.IAMserver.exception.ApiException;
import com.example.IAMserver.exception.ErrorCode;

public class UserNotFoundException extends ApiException {

    private final ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return errorCode.getCode();
    }

    @Override
    public org.springframework.http.HttpStatus getHttpStatus() {
        return errorCode.getStatus();
    }

}
