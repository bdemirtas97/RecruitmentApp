package com.recruitment.app.domain.dto;

import lombok.Value;

@Value
public class MatchAnalyzeRequest {
    String parsed_cv;
    String posting_string;
}
