package com.example.IAMserver.auth.dto;

public record UserLoginRequest(
        String email,
        String password
) {
}
