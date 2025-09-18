package com.example.IAMserver.auth;

import com.example.IAMserver.auth.dto.UserLoginRequest;
import com.example.IAMserver.auth.dto.UserRegistrationRequest;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {

        authService.registerLocalUser(userRegistrationRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) throws JOSEException {

        String token = authService.loginLocalUser(userLoginRequest);

        return ResponseEntity.ok(token);

    }

}
