package com.recruitment.app.domain.service;

import com.recruitment.app.infrastructure.web.dto.PostingVectorRequest;
import com.recruitment.app.infrastructure.web.dto.PostingVectorResponse;
import com.recruitment.app.infrastructure.web.dto.ResumeParsingRequest;
import com.recruitment.app.infrastructure.web.dto.ResumeParsingResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecruitmentAIClient {

    private final WebClient webClient;

    public RecruitmentAIClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResumeParsingResponse fetchParsedResume(String fileUrl) {
            return this.webClient.post()
                    .uri("/resume")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ResumeParsingRequest(fileUrl))
                    .retrieve()
                    .bodyToMono(ResumeParsingResponse.class)
                    .block();
    }

    public PostingVectorResponse fetchPostingVector(String postingStr) {
        return this.webClient.post()
                .uri("/posting")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostingVectorRequest(postingStr))
                .retrieve()
                .bodyToMono(PostingVectorResponse.class)
                .block();
    }
}