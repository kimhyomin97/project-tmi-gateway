package com.tmi.gateway.controller;

import com.tmi.gateway.dto.AuthorizationCodeRequestDto;
import com.tmi.gateway.dto.AuthorizationCodeResponseDto;
import com.tmi.gateway.dto.TokenRequestDto;
import com.tmi.gateway.dto.TokenResponseDto;
import com.tmi.gateway.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> user){
        // TODO : login logic

        Map<String, Object> claims = new HashMap<>();

//        String authorizationCode = tokenProvider
        return null;
    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthorizationCodeResponseDto> authorize(@RequestBody AuthorizationCodeRequestDto authorizationCodeRequestDto){
        log.info("generate authorization code");
        return authService.authorize(authorizationCodeRequestDto);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> token(@RequestBody TokenRequestDto tokenRequestDto){
        log.info("generate token");
        return authService.token(tokenRequestDto);
    }

    @GetMapping("/session-info")
    public ResponseEntity<Map<String, String>> sessionInfo(HttpSession session){
        log.info("session info");
        return authService.getSessionInfo(session);
    }

    @GetMapping("/session-login")
    public ResponseEntity<AuthorizationCodeResponseDto> sessionLogin(@SessionAttribute(name = "loginId") String loginId, HttpSession session){
        log.info("session login");
        return authService.sessionLogin(loginId, session);
    }


}