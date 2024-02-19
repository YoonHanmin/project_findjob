package com.project.findjob.controller;

import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        log.info("@# login()");
        return "/user/login";
    }

    @GetMapping("/regist")
    public String register(){
        log.info("@# login()");
        return "/user/regist";
    }
    @GetMapping("/regg")
    public String regis(){
        log.info("@# login()");
        return "/user/regg";
    }
    @GetMapping("/main_user")
    public String mainuser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("@#@# 권한 ===> "+auth.getAuthorities());
        return "main_user";
    }
}
