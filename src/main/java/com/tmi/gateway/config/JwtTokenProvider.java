package com.tmi.gateway.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Configuration
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret; // 암호화키

    @Value("${jwt.expirationMinutes}")
    private int expirationMinutes; // 토큰 유효시간

    private static final String PRIVATE_KEY = "yourprivatekey";

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

    public KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public String encodePublicKeyToBase64(PublicKey publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        byte[] encodedPublicKey = Base64.getEncoder().encode(publicKeySpec.getModulus().toByteArray());
        return new String(encodedPublicKey);
    }

    public String createIdToken(String issuer, String subject, String audience, long validityMillis, PrivateKey privateKey) throws Exception {
        // 클레임 세트 생성
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subject)
                .audience(audience)
                .expirationTime(new Date(new Date().getTime() + validityMillis))
                .issueTime(new Date())
                .claim("nonce", "random-nonce") // nonce 클레임 추가
                .claim("auth_time", new Date().getTime() / 1000) // 인증 시간 클레임 추가
                .build();

        // 헤더 생성
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();

        // 서명된 JWT 생성
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // 서명 설정
        RSASSASigner signer = new RSASSASigner(privateKey);
        signedJWT.sign(signer);

        // JWT 문자열 반환
        return signedJWT.serialize();
    }
}
