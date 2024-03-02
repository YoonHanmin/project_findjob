package com.project.findjob.controller;

import com.project.findjob.model.Employment;
import com.project.findjob.model.Resume;
import com.project.findjob.model.Store;
import com.project.findjob.repository.EmployRepository;
import com.project.findjob.repository.ResumeRepository;
import com.project.findjob.repository.StoreRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final EmployRepository employRepository;
    private final ResumeRepository resumeRepository;


    @GetMapping("/login")
    public String login(){

        return "/user/login";
    }


    @GetMapping("/regist")
    public String register(){

        return "/user/regist";
    }

    @GetMapping("/main")
    public String main(Model model,Authentication auth){
        String userid = auth.getName();
//        사장님
        Store store =  storeRepository.findByUserid(userid);
        Employment employ = employRepository.findByOwnerid(userid);
        Long user = resumeRepository.count();
        List<Resume> resumeList = resumeRepository.findAll();
        model.addAttribute("resumeList",resumeList);
        model.addAttribute("employ",employ);
        model.addAttribute("user",user);
        model.addAttribute("store",store);
//      유저
        List<Employment> employList = employRepository.findAll();
        Long employCount = employRepository.count();
        boolean resume = resumeRepository.existsByUserid(userid);
        model.addAttribute("employCount",employCount);
        model.addAttribute("resume",resume);
        model.addAttribute("employList",employList);




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
