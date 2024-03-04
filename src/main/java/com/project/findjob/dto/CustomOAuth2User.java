package com.project.findjob.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Data
public class CustomOAuth2User implements OAuth2User {
    public CustomOAuth2User(OAuth2Response oAuth2Response, String role,String userid,boolean enabled,String uname,String profileurl,boolean social,String gender) {
        this.oAuth2Response = oAuth2Response;
        this.userid = userid;
        this.role = role;
        this.enabled = enabled;
        this.uname = uname;
        this.profileurl = profileurl;
        this.social = social;
        this.gender = gender;

    }

    private final OAuth2Response oAuth2Response;
    private final String role;
    private final String userid;
    private Boolean enabled;
    private String uname;
    private String profileurl;
    private String phone;
    private Boolean social;
    private String gender;



    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getUserName(){
        return oAuth2Response.getProvider()+" "+oAuth2Response.getProvider();
    }

}
