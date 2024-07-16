package com.smsoft.mindfulmoment.infrastructure.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    private final HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        String modifiedBy = httpServletRequest.getRequestURI();
        if (!StringUtils.hasText(modifiedBy)) modifiedBy = "unknown";

        return Optional.of(modifiedBy);
    }
}
