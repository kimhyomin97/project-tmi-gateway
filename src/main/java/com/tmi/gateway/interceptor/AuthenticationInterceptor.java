package com.tmi.gateway.interceptor;

import com.tmi.gateway.config.JwtTokenProvider;
import com.tmi.gateway.entity.User;
import com.tmi.gateway.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = jwtTokenProvider.extractJwtTokenFromHeader(request);

        if(ObjectUtils.isEmpty(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }


        User user = userRepository.findByUserSeq(Long.parseLong(jwtTokenProvider.parseJwtToken(token).getSubject())); // TODO :

        if(ObjectUtils.isEmpty(user)) {
            throw new BadRequestException("User not found");
        }

        return true;
    }

    // postHandle

    // afterCompletion

}
