package com.example.IAMserver.JWT;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class JWTClaimsExtractor {

    private final JWTParserService parserService;

    public String getUserId(String token) throws ParseException, JOSEException {
        return parserService.parseToken(token).getSubject();
    }

    public String getEmail(String token) throws JOSEException, ParseException {
        return (String) parserService.parseToken(token).getClaim("email");
    }

    public String getRole(String token) throws JOSEException, ParseException {
        return (String) parserService.parseToken(token).getClaim("role");
    }
}
