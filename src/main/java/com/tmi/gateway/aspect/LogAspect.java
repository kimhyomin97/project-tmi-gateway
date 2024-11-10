package com.tmi.gateway.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.tmi.gateway.controller..*.*(..))")
    public void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logRequestInfo(request);
    }

    private void logRequestInfo(HttpServletRequest request) {
        String info = "";
        info += "┌──────────────────────────────────────────────────\n";
        info += "│ Request URL: " + request.getRequestURL() + "\n";
        info += "│ Request Method: " + request.getMethod() + "\n";
        info += "│ Request IP: " + request.getRemoteAddr() + "\n";
//        info += "│ Request Header: " + header + "\n";
//        info += "│ RequestBody: " + body + "\n";
        info += "└──────────────────────────────────────────────────";
        log.info(info);
    }

    @AfterReturning(pointcut = "cut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        String info = "";
        info += "┌──────────────────────────────────────────────────\n";
        info += "│ Response: " + returnValue + "\n";
        info += "└──────────────────────────────────────────────────";
    }
}
