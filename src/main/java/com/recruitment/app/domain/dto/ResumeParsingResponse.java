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
    ResumeInfo cv_info;

    @Value
    public static class ResumeInfo{
        String first_name;
        String last_name;
        String email;
        String phone;
        String career_field;
        String department;
    }
}
