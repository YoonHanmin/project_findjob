package com.project.findjob.jwt;

import com.project.findjob.model.User;
import com.project.findjob.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Jwt 토큰을 기반으로 다양한 요청에 대한 응답처리를 하기위한 필터
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private  final JWTUtil jwtUtil;

    private final UserDetailService userDetailService;


    @Override
        @Transactional
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
//        request에서 Authorization 헤더를 찾는다. (JwtUtil에서 jwt토큰을  Authorization 헤더에 담았었음)
            String authorization= request.getHeader("Authorization");
            log.info("@# 요청헤더 ==> "+authorization);
//       1. jwt 토큰이 있는지없는지 Authorization 헤더 검증
            if(authorization==null || !authorization.startsWith("Bearer ")){
                log.info("token is null!!!");
                filterChain.doFilter(request,response); // 필터체인으로 다음 필터로 request,response 넘김
//            토큰이 없으므로 메소드 종료(필수임)
                return;
            }
//      2. jwt토큰이 있으면, 토큰을 분리해서 소멸시간을 검증한다.
            String token = authorization.split(" ")[1]; // Bearer 부분 제거
            if(jwtUtil.isExpired(token)){ // 소멸시간이 만료되면
                filterChain.doFilter(request,response);
                return;
            }
//        토큰에서 username과 role 가져옴
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);
            log.info("@# 토큰에서 추출한 username =>" +username);
            log.info("@# 토큰에서 추출한 role =>" +role);
//        user Entity를 생성해서 값을 세팅
           User user = userDetailService.loadUserByUsername(username);

            user.setUserid(username);
            user.setPassword("temppassword");
            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("@#세션등록 완료!!");

    }
}