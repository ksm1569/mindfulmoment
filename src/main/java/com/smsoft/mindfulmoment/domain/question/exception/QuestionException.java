package com.smsoft.mindfulmoment.domain.question.exception;


import com.smsoft.mindfulmoment.domain.common.exception.BusinessException;
import com.smsoft.mindfulmoment.domain.common.exception.ErrorCode;

public class QuestionException extends BusinessException {
    public QuestionException() {
        super(ErrorCode.QUESTION_NOT_EXISTS);
    }
}
