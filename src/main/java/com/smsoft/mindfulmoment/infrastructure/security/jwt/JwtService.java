package com.smsoft.mindfulmoment.infrastructure.security.jwt;

import com.smsoft.mindfulmoment.common.util.CookieUtils;
import com.smsoft.mindfulmoment.domain.user.service.UserService;
import com.smsoft.mindfulmoment.infrastructure.config.AppProperties;
import com.smsoft.mindfulmoment.infrastructure.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final AppProperties appProperties;

    public void createAndSetTokens(Authentication authentication, HttpServletResponse response) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        createAndSetTokens(userPrincipal.getUserDto().getId(), response);
    }

    public void createAndSetTokens(Long userId, HttpServletResponse response) {
        long accessTokenValidity = appProperties.auth().accessTokenExpiration();
        long refreshTokenValidity = appProperties.auth().refreshTokenExpiration();

        String accessToken = tokenProvider.createToken(userId, accessTokenValidity);
        String refreshToken = tokenProvider.createToken(userId, refreshTokenValidity);

        setCookie(response, accessToken, refreshTokenValidity);
        saveRefreshToken(userId, refreshToken, refreshTokenValidity);
    }

    private void setCookie(HttpServletResponse response, String token, long tokenValidity) {
        int maxAge = (int) (tokenValidity);
        CookieUtils.addCookie(response, "access_token", token, maxAge);
    }

    private void saveRefreshToken(Long userId, String refreshToken, long validity) {
        LocalDateTime expiryDate = LocalDateTime.ofInstant(
                Instant.now().plusMillis(validity),
                ZoneId.systemDefault());
        userService.saveRefreshToken(userId, refreshToken, expiryDate);
    }

    public Long getUserIdFromToken(String token) {
        return tokenProvider.getUserIdFromJWT(token);
    }

    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }
}
