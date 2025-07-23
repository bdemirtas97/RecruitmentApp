package com.recruitment.app.domain.model;

import com.recruitment.app.infrastructure.web.dto.CandidateProfileUpdate;
import lombok.*;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class Candidate {
    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final String role;
    private String firstName;
    private String lastName;
    private String contactEmail;
    private String contactPhone;
    private String fileUrl;

    public void updateProfile(CandidateProfileUpdate request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.contactEmail = request.getContactEmail();
        this.contactPhone = request.getContactPhone();
    }

    public void updateResumeUrl(String newUrl){
        this.fileUrl = newUrl;
    }
}