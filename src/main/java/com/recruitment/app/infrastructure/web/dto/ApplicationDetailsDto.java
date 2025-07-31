package com.recruitment.app.infrastructure.web.dto;

import com.recruitment.app.domain.model.Application;
import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ApplicationDetailsDto {
    String candidateFullName;
    String candidateEmail;
    LocalDateTime applicationDate;
    String resumeUrl;
    String score;

    public static ApplicationDetailsDto fromDomain(Application application) {
        String fullName = application.getCandidate().getFirstName() + " " + application.getCandidate().getLastName();
        return new ApplicationDetailsDto(
                fullName,
                application.getCandidate().getContactEmail(),
                application.getCreationDate(),
                application.getCandidate().getFileUrl(),
                application.getScore()
        );
    }
}