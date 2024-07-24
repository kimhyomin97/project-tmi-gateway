package com.tmi.gateway.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @PostMapping("/session")
    public String sessionTest(HttpSession httpSession){
        String id = "test1234";
        httpSession.setAttribute("id", id);
        System.out.println("id = " + httpSession.getAttribute("id"));
        return "session test";
    }

}
