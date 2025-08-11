package com.recruitment.app.domain.dto;

import com.recruitment.app.domain.model.Application;
import lombok.Value;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class ApplicationDetailsDto {
    String candidateFullName;
    String candidateEmail;
    UUID applicationId;
    String contactPhone;
    String softSkills;
    String techSkills;
    String coverLetter;
    LocalDateTime applicationDate;
    String resumeUrl;
    String score;

    public static ApplicationDetailsDto fromDomain(Application application) {
        String fullName = application.getCandidate().getFirstName() + " " + application.getCandidate().getLastName();
        return new ApplicationDetailsDto(
                fullName,
                application.getCandidate().getContactEmail(),
                application.getId(),
                application.getCandidate().getContactPhone(),
                application.getCandidate().getSoftSkills(),
                application.getCandidate().getTechSkills(),
                application.getCoverLetterText(),
                application.getCreationDate(),
                application.getCandidate().getFileUrl(),
                application.getScore()
        );
    }
}