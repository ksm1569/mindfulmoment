package com.smsoft.mindfulmoment.infrastructure.config;

import com.smsoft.mindfulmoment.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.CustomOAuth2UserService;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
                .authorizeRequests()
                .antMatchers("/", "/survey", "/oauth2/**").permitAll()
                .antMatchers("/api/auth/status").permitAll()
                .antMatchers("/api/questions/all", "/api/questions/submit").permitAll()
                .antMatchers("/favicon.ico","/css/**","/js/**", "/images/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        ;

        return http.build();
    }
}
