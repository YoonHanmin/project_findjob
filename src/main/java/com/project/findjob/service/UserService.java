package com.project.findjob.service;

import com.project.findjob.model.User;
import com.project.findjob.repository.ResumeRepository;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResumeRepository resumeRepository;

    public boolean checkUserid(String userid){
        return userRepository.existsByUserid(userid);
    }

    public User save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public void uploadProfile(MultipartFile uploadFile,String filename){
        String uploadFolder = "D:\\dev\\ProjectUpload\\profile";

        String oriname = uploadFile.getOriginalFilename();
//				파일명에서 확장자 제거
        String[] parts = oriname.split("\\.");

//				확장자 제거한 파일명
        String uploadFilename = parts[0];
//		        확장자
        String ext = parts[1];
        String saveFileName = filename.trim();
        filename = saveFileName+"."+ext;
        log.info(filename);
        File saveFile = new File(uploadFolder,filename);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        user.setProfileurl(filename);
        try{
//					transferTo : savaFile 내용을 저장
            uploadFile.transferTo(saveFile);
            userRepository.save(user);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setProfileImg(String profileimg){


    }
}
