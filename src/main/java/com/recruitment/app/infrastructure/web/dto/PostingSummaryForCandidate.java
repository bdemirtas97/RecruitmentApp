package com.recruitment.app.infrastructure.web.dto;

import com.recruitment.app.domain.model.Posting;
import lombok.Value;
import java.util.UUID;

@Value
public class PostingSummaryForCandidate {
    UUID id;
    String title;
    String location;
    String workingType;

    public static PostingSummaryForCandidate fromDomain(Posting posting) {
        return new PostingSummaryForCandidate(
                posting.getId(),
                posting.getTitle(),
                posting.getLocation(),
                posting.getWorkingType()
        );
    }
}