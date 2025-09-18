package com.example.IAMserver.JWT;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JWTKeysService {

    private final RSAPublicKey publicKey;

    public Map<String, Object> getJWKS() {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID("rsa-kid-1") // TODO Change it to rotating keys
                .algorithm(JWSAlgorithm.RS256)
                .build();

        return new JWKSet(rsaKey.toPublicJWK()).toJSONObject(true);
    }

}
