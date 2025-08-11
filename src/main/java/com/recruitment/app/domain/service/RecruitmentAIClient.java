package com.recruitment.app.domain.service;

import com.recruitment.app.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RecruitmentAIClient {
    private final WebClient webClient;

    public ResumeParsingResponse fetchParsedResume(String fileUrl) {
            return this.webClient.post()
                    .uri("/parsed-resumes")
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

    public MatchAnalyzeResponse fetchAnalyzeResult(String parsedCv, String parsedPosting) {
        return this.webClient.post()
                .uri("/match-analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new MatchAnalyzeRequest(parsedCv,parsedPosting))
                .retrieve()
                .bodyToMono(MatchAnalyzeResponse.class)
                .block();
    }
}