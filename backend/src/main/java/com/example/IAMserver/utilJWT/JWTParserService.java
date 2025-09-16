package com.example.IAMserver.utilJWT;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTParserService {

    private final RSAPublicKey publicKey;

    public JWTClaimsSet parseToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Not valid JWT signature");
        }

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationTime != null && expirationTime.before(new Date())) {
            throw new JOSEException("Token expired");
        }

        return signedJWT.getJWTClaimsSet();
    }
}

