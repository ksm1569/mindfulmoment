package com.smsoft.mindfulmoment.presentation.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthApiStatusResponse {
    private boolean isAuthenticated;
}
