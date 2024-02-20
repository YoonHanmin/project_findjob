package com.project.findjob.controller;

import com.project.findjob.model.User;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

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

    @GetMapping("/main")
    public String main(){
        return "main";
           }
    @GetMapping("/user/profile")
    public String profile(){
        return "user/profile";
           }

    @GetMapping("/main_owner")
    public String mainowener(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("@@# getName() ==>"+auth.getName());
        Optional<User> user = userRepository.findByUserid(auth.getName());
        User my = user.get();
        log.info("roles ==>"+my.getRoles());
        log.info("@#@# getAuthorise() ===> "+auth.getAuthorities());

        return "main_owner";
    }
}
