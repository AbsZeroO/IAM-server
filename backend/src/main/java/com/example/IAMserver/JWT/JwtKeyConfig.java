package com.example.IAMserver.JWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtKeyConfig {

    @Value("${security.jwt.private-key}")
    private String privateKeyPath;

    @Value("${security.jwt.public-key}")
    private String publicKeyPath;

    @Bean
    public RSAPrivateKey rsaPrivateKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(privateKeyPath);

        try (InputStream inputStream = resource.getInputStream()) {
            return RsaKeyConverters.pkcs8().convert(inputStream);
        }
    }

    @Bean
    public RSAPublicKey rsaPublicKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(publicKeyPath);

        try (InputStream inputStream = resource.getInputStream()) {
            return RsaKeyConverters.x509().convert(inputStream);
        }
    }
}