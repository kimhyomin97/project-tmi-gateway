package com.tmi.gateway.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class TokenProvider {
    private final long tokenOffset = 3600000; // 1시간
    private String secret = "secret_key_1234";
    public String createToken(String name, Map<String, Object> claims, String host){
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        Date expiration = new Date(zdt.toInstant().toEpochMilli() + tokenOffset);
        return Jwts.builder()
                .setSubject(name)
                .setClaims(claims)
                .setAudience(host)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token){
        //
        return false;
    }
}
