package com.cos.delimo.security;

import com.cos.delimo.dto.AuthenticationDto;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class SecurityService {
    private static final String SECRET_KEY = "delimo-service-secret-key-for-signin-login-authentication-service";
    private static final Integer tokenValidityInSeconds =  20 * 60 * 100000;
    private static final String TOKEN_EXPIRED = "만료된 토큰입니다.";
    private static final String TOKEN_UNVALID = "유효하지 않은 토큰입니다.";

    // 토큰 구현 메서드
    public String createToken(AuthenticationDto auth) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec SigninKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(auth.getEmail())
                .claim("password", auth.getPassword())
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
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new JwtException(TOKEN_UNVALID);
        }
        return claims.getSubject();

    }
}
