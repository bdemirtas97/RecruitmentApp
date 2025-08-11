package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.domain.dto.CandidateProfileUpdate;
import lombok.Getter;

import java.io.IOException;

@Getter
public class ResumeUploadException extends IOException {
    private final CandidateProfileUpdate requestDto;
    private final String s3Message;
    public ResumeUploadException(String message, String s3Message, CandidateProfileUpdate request) {
        super(message);
        this.s3Message = s3Message;
        this.requestDto = request;
    }
}
