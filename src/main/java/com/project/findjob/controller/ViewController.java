package com.project.findjob.controller;

import com.project.findjob.model.Employment;
import com.project.findjob.model.Resume;
import com.project.findjob.model.Store;
import com.project.findjob.model.User;
import com.project.findjob.repository.EmployRepository;
import com.project.findjob.repository.ResumeRepository;
import com.project.findjob.repository.StoreRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @GetMapping("/profile/{username}")
    @ResponseBody
    public ResponseEntity<byte[]> getFileuser(@PathVariable("username") String username){
        User user = userRepository.findByUname(username);
        String profileurl = user.getProfileurl();
//			업로드 파일 경로 + 이름
        File file = new File("D:\\dev\\ProjectUpload\\profile\\"+profileurl);
        ResponseEntity<byte[]> result = null;
        HttpHeaders header = new HttpHeaders();
        try {
//			HttpHeaders 객체 생성후 add(컨텐츠타입,경로)메소드로 파일타입을 HTTP 헤더에 추가
            header.add("Content-Type", Files.probeContentType(file.toPath()));
//				파일 정보를 byte 배열로 복사+헤더정보+http상태 정상을 결과에 저장
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

}
