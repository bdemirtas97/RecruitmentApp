package com.recruitment.app.domain.dto;

import com.recruitment.app.domain.model.Application;
import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ApplicationSummaryDto {
    String postingTitle;
    LocalDateTime applicationDate;
    String status;

    public static ApplicationSummaryDto fromDomain(Application application) {
        return new ApplicationSummaryDto(
                application.getPosting().getTitle(),
                application.getCreationDate(),
                application.getStatus()
        );
    }
}