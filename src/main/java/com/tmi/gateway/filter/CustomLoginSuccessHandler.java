//package com.tmi.gateway.filter;
//
//import com.tmi.gateway.entity.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//
//@Log4j2
//public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
////        final User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
////        final String token = TokenUtils.generateJwtToken(user);
//    }
//}
