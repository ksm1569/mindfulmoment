package com.smsoft.mindfulmoment.domain.user.exception;

import com.smsoft.mindfulmoment.domain.common.exception.BusinessException;
import com.smsoft.mindfulmoment.domain.common.exception.ErrorCode;

public class AuthenticationException extends BusinessException {
    public AuthenticationException() {
        super(ErrorCode.AUTHENTICATION_FAILED);
    }
}