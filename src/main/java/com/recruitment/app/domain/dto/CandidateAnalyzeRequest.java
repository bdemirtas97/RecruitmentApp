package com.recruitment.app.domain.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class CandidateAnalyzeRequest {
    UUID candidateId;
}
