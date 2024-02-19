package com.project.findjob.service;

import com.project.findjob.model.User;
import com.project.findjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailService implements UserDetailsService {
//    RequiredArgs~ : 생성자 주입 방식
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String userid) throws UsernameNotFoundException {
        log.info("@#@# userid ==>>"+userid);
       Optional<User> userOptional = userRepository.findByUserid(userid);
        if (userOptional.isEmpty()) {
            log.info("@#@# user Empty!!!");
            throw new UsernameNotFoundException("@#User not found with userid: " + userid);
        }
            User user = userOptional.get();
            log.info("@#@ user는" + user.getUserid());

        return user;
    }
}
