package com.tmi.gateway.service;

import com.tmi.gateway.dto.AuthorizationCodeRequestDto;
import com.tmi.gateway.dto.AuthorizationCodeResponseDto;
import com.tmi.gateway.dto.TokenRequestDto;
import com.tmi.gateway.dto.TokenResponseDto;
import com.tmi.gateway.entity.User;
import com.tmi.gateway.repository.UserRepository;
import com.tmi.gateway.util.SessionInfo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    public ResponseEntity<Map<String, String>> login(Map<String, String> user){
        // pw + salt -> hash -> db비교

        // id/pw 검증 통과 -> 인증코드 발급?
        return null;
    }

    /**
     * 인증코드 발급
     * @param authorizationCodeRequestDto
     * @return
     */
    public ResponseEntity<AuthorizationCodeResponseDto> authorize(AuthorizationCodeRequestDto authorizationCodeRequestDto){
        // UUID 생성
        String code = UUID.randomUUID().toString();
        // TODO: UUID 검증로직 추가 필요

        // TODO: 로그인 정책 검증로직 추가 필요
        String state = "state";

        AuthorizationCodeResponseDto authorizationCodeResponseDto = AuthorizationCodeResponseDto.builder()
                .code(code)
                .state(state)
                .build();
        return ResponseEntity.ok(authorizationCodeResponseDto);
    }

    /**
     * 토큰 발급
     * @param tokenRequestDto
     * @return
     */
    public ResponseEntity<TokenResponseDto> token(TokenRequestDto tokenRequestDto){
        
        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                .accessToken("accessToken")
                .refreshToken("refresh")
                .expiresIn("3600")
                .refreshTokenExpiresIn("86400")
                .scope("scope")
                .tokenType("Bearer")
                .idToken("idToken")
                .build();
        return ResponseEntity.ok(tokenResponseDto);
    }

    public ResponseEntity<Map<String, String>> getSessionInfo(HttpSession session){
        // 세션정보 검증 -> TODO : filter에서 검증하도록 수정
        User user = userRepository.findByUserSeq((Long)session.getAttribute(SessionInfo.USERSEQ));
        if(user == null){
            return null;
        }

        // 세션정보 반환
        Map<String, String> sessionInfo = Map.of(
                "userSeq", user.getUserSeq().toString()
//                "loginId", user.getLoginId(), // FE 세션으로 로그인 버튼에 필요
        );
        return ResponseEntity.ok(sessionInfo);
    }

    public ResponseEntity<AuthorizationCodeResponseDto> sessionLogin(String loginId, HttpSession session){
        // 세션정보 검증 -> TODO : filter에서 검증하도록 수정
        User user = userRepository.findByUserSeq((Long)session.getAttribute(SessionInfo.USERSEQ));
        if(user == null){
            return null;
        }

        // 세션정보로 인증코드 발급
        AuthorizationCodeResponseDto authorizationCodeResponseDto = AuthorizationCodeResponseDto.builder()
                .code(UUID.randomUUID().toString())
                .state("state")
                .build();

        return authorize(new AuthorizationCodeRequestDto());
    }
}
