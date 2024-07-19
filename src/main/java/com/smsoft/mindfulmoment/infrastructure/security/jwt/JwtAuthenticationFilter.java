package com.smsoft.mindfulmoment.infrastructure.security.jwt;

import com.smsoft.mindfulmoment.common.util.CookieUtils;
import com.smsoft.mindfulmoment.domain.common.exception.BusinessException;
import com.smsoft.mindfulmoment.domain.user.exception.AuthenticationException;
import com.smsoft.mindfulmoment.domain.user.service.UserService;
import com.smsoft.mindfulmoment.infrastructure.security.userdetails.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request).orElse(null);
            if (token != null) {
                if (jwtService.validateToken(token)) {
                    Long userId = jwtService.getUserIdFromToken(token);
                    setAuthenticationToContext(userId, request);
                } else {
                    throw new AuthenticationException();
                }
            }
        } catch (ExpiredJwtException ex) {
            handleExpiredToken(request, response, ex);
        } catch (AuthenticationException ex) {
            log.error("Authentication failed: {}", ex.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Authentication failed");
        } catch (BusinessException ex) {
            log.error("Business exception occurred: {}", ex.getMessage());
            sendErrorResponse(response, HttpStatus.BAD_REQUEST, ex.getMessage());
            return;
        } catch (Exception ex) {
            log.error("Unexpected error occurred during authentication", ex);
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException ex) throws IOException {
        try {
            Long userId = Long.parseLong(ex.getClaims().getSubject());
            if (jwtService.validateAndRefreshTokenIfNeeded(userId, response)) {
                setAuthenticationToContext(userId, request);
                log.debug("Access token refreshed successfully for user: {}", userId);
            } else {
                log.debug("Invalid refresh token. User needs to re-authenticate.");
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired and couldn't be refreshed");
            }
        } catch (Exception e) {
            log.error("Error while handling expired token", e);
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error processing authentication");
        }
    }
    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return CookieUtils.getCookie(request, "access_token")
                .map(Cookie::getValue);
    }
    private void setAuthenticationToContext(Long userId, HttpServletRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = String.format("{\"error\":\"%s\",\"message\":\"%s\"}", status.getReasonPhrase(), message);
        response.getWriter().write(json);
    }

}
