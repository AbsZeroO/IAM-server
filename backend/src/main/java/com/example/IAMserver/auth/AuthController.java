package com.example.IAMserver.auth;

import com.example.IAMserver.dto.UserRegistrationRequest;
import com.example.IAMserver.user.UserEntityDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserEntityDetailsService userEntityDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {

        userEntityDetailsService.registerUser(userRegistrationRequest);

        return ResponseEntity.ok().build();
    }

}
