package com.recruitment.app.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class PostingCreationRequest {
    @NotBlank private String title;
    @NotBlank private String location;
    @NotBlank private String level;
    @NotBlank private String workingType;
    @NotBlank private String workPlace;
    @NotBlank private String details;
    @NotNull(message = "You must select a hiring manager")
    private UUID hiringManagerId;
    @NotBlank private String keywords;
}
