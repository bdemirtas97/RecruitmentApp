package com.recruitment.app.domain.dto;

import lombok.Value;
import java.util.List;

@Value
public class ResumeParsingResponse {
    String status;
    List<String> soft_skills;
    List<String> tech_skills;
    float[] parsed_cv_vector;
    String parsed_cv_text;
}
