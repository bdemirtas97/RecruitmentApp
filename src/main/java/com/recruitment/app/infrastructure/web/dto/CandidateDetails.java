package com.recruitment.app.infrastructure.web.dto;

import com.recruitment.app.domain.model.Candidate;
import lombok.Value;

@Value
public class CandidateDetails {
    String email;
    String firstName;
    String lastName;
    String contactEmail;
    String contactPhone;

    public static CandidateDetails fromDomain(Candidate candidate) {
        return new CandidateDetails(
                candidate.getEmail(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getContactEmail(),
                candidate.getContactPhone()
        );
    }
}