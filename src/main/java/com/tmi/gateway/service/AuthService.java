package com.tmi.gateway.service;

import com.tmi.gateway.dto.AuthorizationCodeRequestDto;
import com.tmi.gateway.dto.AuthorizationCodeResponseDto;
import com.tmi.gateway.dto.TokenRequestDto;
import com.tmi.gateway.dto.TokenResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AuthService {
    public ResponseEntity<Map<String, String>> login(Map<String, String> user);
    public ResponseEntity<AuthorizationCodeResponseDto> authorize(AuthorizationCodeRequestDto authorizationCodeRequestDto);
    public ResponseEntity<TokenResponseDto> token(TokenRequestDto tokenRequestDto);

    public ResponseEntity<Map<String, String>> getSessionInfo(HttpSession session);

    public ResponseEntity<AuthorizationCodeResponseDto> sessionLogin(String loginId, HttpSession session);
}
