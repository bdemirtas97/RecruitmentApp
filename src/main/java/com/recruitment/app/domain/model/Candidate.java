package com.recruitment.app.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public void updateProfile(String firstName, String lastName, String contactEmail, String contactPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public void updateResumeUrl(String newUrl){
        this.fileUrl = newUrl;
    }
}