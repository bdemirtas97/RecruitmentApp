package com.recruitment.app.infrastructure.web.dto;

import lombok.Value;

@Value
public class PostingVectorResponse {
    private float[] embedding;
}
