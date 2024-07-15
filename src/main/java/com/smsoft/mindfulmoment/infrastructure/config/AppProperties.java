package com.smsoft.mindfulmoment.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "app")
@ConstructorBinding
public record AppProperties(Auth auth, OAuth2 oAuth2) {
    public record Auth(String tokenSecret, long accessTokenExpiration, long refreshTokenExpiration) {
    }
    public record OAuth2(List<String> authorizedRedirectUris) {
    }
}