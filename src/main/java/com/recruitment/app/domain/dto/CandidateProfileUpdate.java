package com.recruitment.app.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CandidateProfileUpdate {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Email private String contactEmail;
    @NotBlank private String contactPhone;

    public static CandidateProfileUpdate fromDetails(CandidateDetails details) {
        CandidateProfileUpdate update = new CandidateProfileUpdate();
        update.setFirstName(details.getFirstName());
        update.setLastName(details.getLastName());
        update.setContactEmail(details.getContactEmail());
        update.setContactPhone(details.getContactPhone());
        return update;
    }
}