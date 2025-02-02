package com.smsoft.mindfulmoment.infrastructure.security.oauth2;

import com.smsoft.mindfulmoment.domain.user.exception.OAuth2AuthenticationProcessingException;
import com.smsoft.mindfulmoment.domain.user.entity.AuthProvider;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.user.GoogleOAuth2UserInfo;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.user.KakaoOAuth2UserInfo;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.user.NaverOAuth2UserInfo;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.user.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.NAVER.toString())) {
            return new NaverOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.KAKAO.toString())) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
