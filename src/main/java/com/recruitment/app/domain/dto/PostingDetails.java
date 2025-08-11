package com.recruitment.app.domain.dto;

import com.recruitment.app.domain.model.Posting;
import lombok.Value;

@Value
public class PostingDetails {
    String title;
    String details;
    String location;
    String level;
    String status;
    String workingType;
    String workPlace;

    public static PostingDetails fromDomain(Posting posting) {
        return new PostingDetails(
                posting.getTitle(),
                posting.getDetails(),
                posting.getLocation(),
                posting.getLevel(),
                posting.getStatus(),
                posting.getWorkingType(),
                posting.getWorkPlace()
        );
    }
}
