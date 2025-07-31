package com.recruitment.app.infrastructure.web.dto;

import lombok.Value;
import java.util.List;

@Value
public class ResumeParsingResponse {
    String status;
    List<String> softSkills;
    List<String> techSkills;
    float[] embedding;
}
