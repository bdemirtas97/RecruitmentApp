package com.recruitment.app.domain.dto;

import lombok.Value;

@Value
public class PostingVectorsResponse {
    String status;
    float[][] posting_vectors;
}
