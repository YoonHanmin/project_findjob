package com.project.findjob.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

@Component

public class SocialClientRegistration {
    public ClientRegistration naverClientRegistration() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId("FY_2PhVU0W7Mrj1wwzdm")
                .clientSecret("DwjCyzmDEo")
                .redirectUri("http://localhost:9999/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email", "gender", "birthday", "birthyear", "mobile")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }
}
