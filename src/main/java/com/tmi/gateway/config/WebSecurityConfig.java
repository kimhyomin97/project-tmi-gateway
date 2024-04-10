package com.tmi.gateway.config;

import com.tmi.gateway.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig { // WebSecurityConfigurerAdapter deprecated
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("##### WebSecurityConfig start");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 토큰을 사용하는 경우 모든 요청을 허용
//                .addFilterBefore(jwtAuthenticationFilter(), SecurityFilterChain.class) // jwt 필터 추가
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함
                .formLogin(formLogin -> formLogin.disable()); // form login 비활성화
        log.info("##### WebSecurityConfig end");
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthenticationFilter() {
        return new JwtAuthorizationFilter();
    }
}