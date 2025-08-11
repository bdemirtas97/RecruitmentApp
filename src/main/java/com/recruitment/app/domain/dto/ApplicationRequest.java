package com.recruitment.app.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ApplicationRequest {
    @Size(max = 5000, message = "Cover letter cannot exceed 5000 characters")
    private String coverLetterText;
}