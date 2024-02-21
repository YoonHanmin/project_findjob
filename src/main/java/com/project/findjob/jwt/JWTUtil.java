package com.project.findjob.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private SecretKey secretKey;
    //    하드코딩된 비밀키를 가지고 개인키를 만듬
    public  JWTUtil(@Value("${spring.jwt.secret}") String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    // token을 secretKey를 가지고 검증한뒤, Payload()를 통해 username을 String타입으로 가져온다.
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username",String.class);
    }
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    //    Jwt 토큰을 빌터패턴으로 생성하는 메소드
    public String createJwt(String username,String role,Long expiredMs){
        return Jwts.builder()
                .claim("username",username)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행시간은 현재시간
                .expiration(new Date(System.currentTimeMillis()+ expiredMs)) // 만료시간은 현재시간 + 만료기간
                .signWith(secretKey) // 비밀키로 서명한다.
                .compact();
    }

}