package com.example.IAMserver.dto;

public record UserRegistrationRequest(
        String email,
        String password,
        String username
) {
}
