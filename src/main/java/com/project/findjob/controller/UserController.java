package com.project.findjob.controller;

import com.project.findjob.model.Role;
import com.project.findjob.model.User;
import com.project.findjob.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // 아이디 중복확인
    @GetMapping("/regist/exists/{userid}")
    @ResponseBody
    public ResponseEntity<Boolean> checkId(@PathVariable("userid") String userid){
        boolean a = userService.checkUserid(userid);
        log.info("@# 아이디 ==>>"+a);
        return ResponseEntity.ok(userService.checkUserid(userid));
    }


    @PostMapping("/regist")
    public String regist(User user){
        log.info("@# User ==>"+user.getUserid());
        log.info("@# UserPW ==>"+user.getPassword());
        log.info("@# UserType ==>"+user.getType());
        if(user.getType().equals("user")){
        Role role = new Role();
        role.setId(1L); // ROLE_USER
        user.getRoles().add(role);
        } else if(user.getType().equals("owner")){
            Role role = new Role();
            role.setId(2L); // ROLE_OWNER
            user.getRoles().add(role);
        }
        userService.save(user);
    return "redirect:/login";
    }
}
