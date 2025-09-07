package com.example.IAMserver.user;

import com.example.IAMserver.exception.ApiException;
import com.example.IAMserver.exception.ErrorCode;

public class UserAlreadyExistsException extends ApiException {

    private final ErrorCode errorCode = ErrorCode.USER_ALREADY_EXISTS;

    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists");
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
