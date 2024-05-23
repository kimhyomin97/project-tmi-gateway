package com.tmi.gateway.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

public class JwtTokenProviderTest {

    @Test
    @DisplayName("id token 생성 테스트")
    void createIdToken() {
        // given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        KeyPair keyPair;
        try {
            keyPair = jwtTokenProvider.generateRSAKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // when
        String idToken;
        try {
            idToken = jwtTokenProvider.createIdToken("tmi", "tmi", "tmi", 1000 * 60 * 60 * 24, keyPair.getPrivate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // then
        System.out.println("idToken = " + idToken);
    }
}
