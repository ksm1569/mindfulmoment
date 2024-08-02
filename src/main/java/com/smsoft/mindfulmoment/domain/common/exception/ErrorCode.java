package com.smsoft.mindfulmoment.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Input Value"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "Internal Server Error"),

    // Authentication
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "A001", "Authentication Failed"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "A002", "Unauthorized"),

    // User
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U001", "Invalid user information"),

    // ADHD Score
    ADHD_SCORE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AD001", "Invalid ADHD score information"),
    SURVEY_ALREADY_SUBMITTED(HttpStatus.CONFLICT, "AD002", "Survey has already been submitted"),

    // Question
    QUESTION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "Q001", "Invalid question information");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}