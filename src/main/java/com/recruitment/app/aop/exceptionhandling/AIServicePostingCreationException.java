package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.domain.dto.CandidateProfileUpdate;
import com.recruitment.app.domain.dto.PostingCreationRequest;
import lombok.Getter;

@Getter
public class AIServicePostingCreationException extends RuntimeException {
    private final PostingCreationRequest requestDto;
    private final String webClientMessage;
    private final String responseBody;
    public AIServicePostingCreationException(String message, String webClientMessage, String responseBody, PostingCreationRequest request) {
        super(message);
        this.webClientMessage = webClientMessage;
        this.requestDto = request;
        this.responseBody = responseBody;
    }
}
