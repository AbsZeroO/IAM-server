package com.example.IAMserver.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException  {
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }

    protected abstract String getErrorCode();
    protected abstract HttpStatus getHttpStatus();
}
