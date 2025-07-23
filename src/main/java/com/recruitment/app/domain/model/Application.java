package com.recruitment.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class Application {
    private UUID id;
    private Candidate candidate;
    private Posting posting;
    private LocalDateTime creationDate;
    private String coverLetterText;
    private String status;
    private String score;
}
