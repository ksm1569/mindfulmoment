package com.smsoft.mindfulmoment.infrastructure.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@EnableJpaAuditing
@Configuration
public class JpaConfig {
    private final HttpServletRequest httpServletRequest;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl(httpServletRequest);
    }
}
