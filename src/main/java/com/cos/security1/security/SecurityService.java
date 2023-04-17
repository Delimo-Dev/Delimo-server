package com.cos.security1.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class SecurityService {
    private static final String SECRET_KEY = "qwertyuiopasdfghjklqwertyuiopasdfghjklzxcvbnmzxcvbnm";

    // 토큰 구현 메서드
    public String createToken(String subject, long expTime) {
        if (expTime <= 0) {
            throw new RuntimeException("expTime needed to be bigger than zero");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec SigninKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .signWith(SigninKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public String getSubject(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "유효 하지 않은 토큰");
        } catch (JwtException e) {
            throw new JwtException("유효하지 않은 토큰");
        }
        return claims.getSubject();

    }
}
