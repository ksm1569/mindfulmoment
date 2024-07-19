package com.smsoft.mindfulmoment.domain.adhdscore.exception;

import com.smsoft.mindfulmoment.domain.common.exception.BusinessException;
import com.smsoft.mindfulmoment.domain.common.exception.ErrorCode;

public class AdhdScoreException extends BusinessException {
    public AdhdScoreException() {
        super(ErrorCode.ADHD_SCORE_NOT_EXISTS);
    }
}
