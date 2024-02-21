package com.project.findjob.jwt;

import com.project.findjob.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

// formlogin을 비활성화 했기때문에 시큐리티에서 로그인을 진행해주는  UsernamePasswordAuthenticationFilter을 구현
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.authenticationManager=authenticationManager;
        this.jwtUtil= jwtUtil;
    }

    //    UserDetailService가 DB에서 가져온 유저정보를 비교해서 로그인을 수행해주는 객체
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
//        클라이언트로부터 id,pw를 받아온다.
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        log.info("@#login Filter ==>"+username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password,null);
        return authenticationManager.authenticate(authToken);
    }

    //    로그인 성공시 유저권한정보를 가져와 해당정보로 jwt 토큰을 생성해준다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        log.info("sucess~!!!!");


//            Authentication을 통해 유저정보,권한등을 가져올수있다.
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
//      UserDetail의 getAuthorities를 통해 유저의 권한정보를 뽑아내서 Jwt에 넣기위해 String으로 변환
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
//        jwt토큰 생성
        String token = jwtUtil.createJwt(username, role, 60*60*10L);
//        http 응답 헤더에 jwt토큰을 넣는다.
        response.addHeader("Authorization", "Bearer " + token);
            log.info("@# 생성된 토큰 =>"+token);



    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("fail....TT");
//        로그인 실패시 401코드 반환
        response.setStatus(401);
    }
}