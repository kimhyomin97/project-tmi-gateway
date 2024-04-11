package com.tmi.gateway.config;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.Date;

@Slf4j
@Configuration
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret; // 암호화키

    @Value("${jwt.expirationMinutes}")
    private int expirationMinutes; // 토큰 유효시간

    public String createJwtToken(String userSeq) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMinutes * 60 * 1000);

        log.info("토큰 발급 userId: {}", userSeq);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("tmi")
                .setSubject(userSeq)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Claims parseJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("토큰 검증 실패", e);
            return null;
        }
    }

    public String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
