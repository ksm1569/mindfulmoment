package com.smsoft.mindfulmoment.presentation.api.auth;

import com.smsoft.mindfulmoment.common.util.CookieUtils;
import com.smsoft.mindfulmoment.presentation.api.auth.dto.AuthApiStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequestMapping("/api/auth")
@RestController
public class AuthApiController {
    @GetMapping("/status")
    public ResponseEntity<AuthApiStatusResponse> getAuthStatus(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());

        if (isAuthenticated) {
            Optional<Cookie> cookie = CookieUtils.getCookie(request, "access_token");
            if (cookie.isEmpty() || cookie.get().getValue().isEmpty()) {
                isAuthenticated = false;
            }
        }

        return ResponseEntity.ok(new AuthApiStatusResponse(isAuthenticated));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            CookieUtils.deleteCookie(request, response, "access_token");
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}
