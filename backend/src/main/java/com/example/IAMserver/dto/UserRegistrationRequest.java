package com.example.IAMserver.dto;

public record UserRegistrationRequest(
        String email,
        String username,
        String firstName,
        String lastName,
        String password
) {
}
