package com.project.findjob.dto;

public interface OAuth2Response {
//    naver? kakao?
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getGender();


}
