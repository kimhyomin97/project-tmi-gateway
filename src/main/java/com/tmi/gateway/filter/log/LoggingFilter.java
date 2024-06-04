package com.tmi.gateway.filter.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long traceId = UUID.randomUUID().getMostSignificantBits();
        MDC.put("traceId", String.valueOf(traceId));
        if(isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(new RequestWrapper(request), new ResponseWrapper(response), filterChain);
        }
        MDC.clear();
    }

    protected void doFilterWrapped(RequestWrapper request, ResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
        try{
            logRequest(request);
            filterChain.doFilter(request, response);
        } finally {
            logResponse(response);
        }
    }

    private void logRequest(HttpServletRequest request) {
        String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        log.info("Request: {} uri=[{}] contentType=[{}]", request.getMethod(), request.getRequestURI() + queryString, request.getContentType());
    }

    private void logResponse(ResponseWrapper response) {
        log.info("Response: status=[{}] contentType=[{}]", response.getStatus(), response.getContentType());
    }

}
