package com.project.findjob.service;

import com.project.findjob.dto.CustomOAuth2User;
import com.project.findjob.dto.GoogleResponse;
import com.project.findjob.dto.NaverResponse;
import com.project.findjob.dto.OAuth2Response;
import com.project.findjob.model.Role;
import com.project.findjob.model.User;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService  extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("@# oAuth2User 특성 ==>" + oAuth2User.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }
       String role = "ROLE_USER";
//        이메일에서 아이디부분을 유저아이디로 저장
        String email = oAuth2Response.getEmail();
        String[] parts = email.split("@");
        String userid = parts[0];

        String username = oAuth2Response.getName();
        String gender = oAuth2Response.getGender();

        log.info("네이버 이름은 ===>>"+username);
        User existData = userRepository.findByUname(username);
        if(existData==null){
            User user = new User();
            user.setUserid(userid);
            user.setEnabled(false);
            user.setSocial(true);
            user.setUname(oAuth2Response.getName());
            user.setProfileurl("default.jpg");
            user.setGender(gender);

            user.setUseremail(oAuth2Response.getEmail());
            Role userrole = new Role();
            userrole.setId(1L); // ROLE_USER
            user.getRoles().add(userrole);
            userRepository.save(user);
            log.info("@# 신규회원 ==>");
            return new CustomOAuth2User(oAuth2Response,role,userid,false,username,"default.jpg",true,gender);
        }

            log.info("#@ 기존 로그인 회원 ===>");

        return oAuth2User;

//        User객체를 기반으로 Oauth2유저 객체 생성

    }
}
