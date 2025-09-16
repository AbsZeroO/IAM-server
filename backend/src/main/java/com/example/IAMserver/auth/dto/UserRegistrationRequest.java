package com.example.IAMserver.auth.dto;

public record UserRegistrationRequest(
        String email,
        String password,
        String username
) {
}
