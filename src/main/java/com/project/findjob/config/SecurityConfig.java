package com.project.findjob.config;

import com.project.findjob.oauth2.CustomClientRegistrationRepo;
import com.project.findjob.oauth2.CustomOAuth2AuthorizedClientService;
import com.project.findjob.service.CustomOAuth2UserService;
import com.project.findjob.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Autowired
    UserDetailService userDetailService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService
            ,CustomClientRegistrationRepo customClientRegistrationRepo,CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService,JdbcTemplate jdbcTemplate) {
        this.customOAuth2AuthorizedClientService = customOAuth2AuthorizedClientService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    private final AuthenticationConfiguration authenticationConfiguration;
//   private final JWTUtil jwtUtil;
//    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,JWTUtil jwtUtil){
//        this.authenticationConfiguration = authenticationConfiguration;
//        this.jwtUtil=jwtUtil;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable());
//        http
//                .formLogin((auth)-> auth.disable());
//        http     // jwt를 사용하기위해 formLogin 비활성화, 로그인필터 커스텀으로 구현
//                .httpBasic((auth)-> auth.disable());
        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login/oauth2/code/naver")
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        .userInfoEndpoint((userInfoEndpointConfig)-> userInfoEndpointConfig.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/main")
                );
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**","/image/**","/regist/**","/js/**",
                                "/login","/login/oauth2/**","/login/oauth2/code/naver").permitAll() // 다음 url은 인증없이 모든 사용자에게 허용
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/owner/**").hasAuthority("ROLE_OWNER")
                        .requestMatchers("/main/**").hasAnyAuthority("ROLE_OWNER","ROLE_USER")
                        .anyRequest().authenticated() // 그 이외의 모든 요청은 인증 필요함
                )
//                허가되지않은 경우 -> /login으로 이동
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/main")

                )
                .logout((logout) -> logout.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login"));
//        http
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http
////                UsernamePass~ 필터자리에 LoginFilter가 들어가야함
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);
//        http
////                JwtFilter 필터는 Login 필터 실행되기 이전에 실행시킴!!
//                .addFilterBefore(new JwtFilter(jwtUtil,userDetailService),LoginFilter.class);
        return http.build();
    }

//    로그인 인증을 수행해주는 AuthenticationManager 객체를 빈에 등록
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
