package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.domain.dto.CandidateProfileUpdate;
import lombok.Getter;

@Getter
public class AIServiceResumeException extends RuntimeException {
    private final CandidateProfileUpdate requestDto;
    private final String webClientMessage;
    private final String responseBody;
    public AIServiceResumeException(String message, String webClientMessage, String responseBody, CandidateProfileUpdate request) {
      super(message);
      this.webClientMessage = webClientMessage;
      this.requestDto = request;
      this.responseBody = responseBody;
    }
}
