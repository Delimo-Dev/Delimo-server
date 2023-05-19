package com.cos.security1.security;

import com.cos.security1.dto.AuthenticationDto;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class SecurityService {
    private static final String SECRET_KEY = "qwertyuiopasdfghjklqwertyuiopasdfghjklzxcvbnmzxcvbnm";
    private static final Integer tokenValidityInSeconds = 20 * 60 * 1000;


    // 토큰 구현 메서드
    public String createToken(AuthenticationDto auth) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec SigninKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(auth.getEmail())
                .setSubject(auth.getPassword())
                .signWith(SigninKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInSeconds))
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
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "만료된 토큰");
        } catch (JwtException e) {
            throw new JwtException("유효 하지 않은 토큰");
        }
        return claims.getSubject();

    }
}
