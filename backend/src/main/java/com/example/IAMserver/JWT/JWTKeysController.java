package com.example.IAMserver.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/.well-known/jwks.json")
@RequiredArgsConstructor
public class JWTKeysController {

    private final JWTKeysService jwtKeysService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getKeys() {
        Map<String, Object> keys = jwtKeysService.getJWKS();

        return ResponseEntity.ok(keys);
    }

}
