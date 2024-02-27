package com.project.findjob.controller;

import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login(){

        return "/user/login";
    }


    @GetMapping("/regist")
    public String register(){

        return "/user/regist";
    }

    @GetMapping("/main")
    public String main(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user= (User) authentication.getPrincipal();
//        log.info("@#@# 내 아이디 -==>"+user.getUserid());
//        log.info("@#@# 내 프로필-==>"+user.getProfileurl());

        return "main";
           }
    @GetMapping("/user/profileChange")
    public String profile(){
        return "user/profileChange";
           }

    @GetMapping("/")
    @ResponseBody
    public String mainP() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "Main Controller : "+name;
    }



}
