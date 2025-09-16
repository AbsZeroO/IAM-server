package com.example.IAMserver.utilJWT;

import com.example.IAMserver.user.UserEntity;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${security.jwt.private-key}")
    private RSAPrivateKey privateKey;

    @Value("${security.jwt.public-key}")
    private RSAPublicKey publicKey;

    @Value("${app.jwt.expiration}")
    private int expiration;

    public String generateToken(UserEntity userEntity) throws JOSEException {
        JWSSigner signer = new RSASSASigner(privateKey);

        Instant nowUTC = Instant.now();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("IAM-server")
                .jwtID(UUID.randomUUID().toString())
                .issueTime(Date.from(nowUTC))
                .expirationTime(Date.from(nowUTC.plusSeconds(expiration)))
                .subject(userEntity.getId().toString())
                .claim("email", userEntity.getEmail())
                .claim("role", userEntity.getRoles())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID("rsa-kid-1")
                        .build(),
                jwtClaimsSet
        );


        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

}
