package com.recruitment.app.domain.dto;

import com.recruitment.app.domain.model.Posting;
import lombok.Value;
import java.util.UUID;

@Value
public class PostingSummary {
    UUID id;
    String title;
    String status;
    String hiringManagerName;

    public static PostingSummary fromDomain(Posting domain){
        return new PostingSummary(
                domain.getId(),
                domain.getTitle(),
                domain.getStatus(),
                "%s %s".formatted(domain.getHiringManager().getFirstName(),domain.getHiringManager().getLastName())
        );
    }
}
