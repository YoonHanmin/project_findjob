package com.project.findjob.service;

import com.project.findjob.model.User;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailService implements UserDetailsService {
//    RequiredArgs~ : 생성자 주입 방식
private final UserRepository userRepository;

@Override
@Transactional // Transactional : roles 테이블이 지연로딩되므로 영속성컨텍스트 문제를 해결하기위해 트랜젝션 처리
public User loadUserByUsername(String userid) throws UsernameNotFoundException {
    log.info("@#@# userid ==>>"+userid);
    Optional<User> userOptional = userRepository.findByUserid(userid);
    if (userOptional.isEmpty()) {
        log.info("@#@# user Empty!!!");
        throw new UsernameNotFoundException("@#User not found with userid: " + userid);
    }
    User user = userOptional.get();

    log.info("@#@ user가 가진 권한정보는" + user.getProfileurl());
    log.info("@# 권한정보 ==>"+user.getRoles().get(0));

    return user;
}
    public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User) {
        // OAuth 2.0을 통해 가져온 사용자 정보에서 필요한 정보를 추출
        String oauth2Username = oAuth2User.getAttribute("name");
        String oauth2Email = oAuth2User.getAttribute("email");
        // 기타 필요한 정보들을 추출
        User user = userRepository.findByUname(oauth2Username);


        return user;
    }



}
