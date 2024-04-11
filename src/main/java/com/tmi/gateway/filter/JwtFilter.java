package com.tmi.gateway.filter;

import com.tmi.gateway.config.JwtTokenProvider;
import com.tmi.gateway.entity.User;
import com.tmi.gateway.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    // OncePerRequestFilter : 모든 요청에 대해 단 한번만 실행되도록 보장하는 필터
    // GenericFilterBean을 사용해도 무방
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.extractJwtTokenFromHeader(request);

        if(ObjectUtils.isEmpty(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = userRepository.findByUserSeq(Long.parseLong(jwtTokenProvider.parseJwtToken(token).getSubject()));

        if(ObjectUtils.isEmpty(user)) {
            throw new BadRequestException("User not found");
        }

        filterChain.doFilter(request, response);
    }
}
