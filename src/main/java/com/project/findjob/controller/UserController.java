package com.project.findjob.controller;

import com.project.findjob.model.Role;
import com.project.findjob.model.User;
import com.project.findjob.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

//회원가입
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

//    프로필사진 업로드
    @PostMapping("/user/profile/uplode")
    public  String upload(@RequestParam("uploadFile") MultipartFile file){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("@@# getName() ==>"+auth.getName());
        String filename = auth.getName();
        userService.uploadProfile(file,filename);
        return "redirect:/user/profile";
    }
    @GetMapping("/main/{profileurl}")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@PathVariable("profileurl") String profileurl){
        log.info("@# 프로필 이미지 =>"+profileurl);
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
