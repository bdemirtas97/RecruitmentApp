package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.domain.dto.PostingUpdate;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AIServicePostingUpdateException extends RuntimeException {
    private final PostingUpdate requestDto;
    private final String webClientMessage;
    private final String responseBody;
    private final UUID postingId;
    public AIServicePostingUpdateException(String message, String webClientMessage, String responseBody, PostingUpdate request, UUID postingId) {
        super(message);
        this.webClientMessage = webClientMessage;
        this.requestDto = request;
        this.responseBody = responseBody;
        this.postingId = postingId;
    }
}
