package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.domain.dto.CandidateSignupRequest;
import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final CandidateSignupRequest requestDto;
    public UserAlreadyExistsException(String message, CandidateSignupRequest requestDto) {
        super(message);
        this.requestDto = requestDto;
    }
}
