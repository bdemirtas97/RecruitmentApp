package com.recruitment.app.domain.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class CandidateSearchResult {
    UUID candidateId;
    UUID postingId;
    String candidateFullName;
    String candidateEmail;
    String candidatePhone;
    String softSkills;
    String techSkills;
    String resumeUrl;
    String score;
}
