package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.dto.AnalyzeResponse;
import com.recruitment.app.domain.dto.ApplicationDetailsDto;
import com.recruitment.app.domain.dto.ApplicationSummaryDto;
import java.util.List;
import java.util.UUID;

public interface ApplicationUseCasePort {
    void applyForPosting(String candidateEmail, UUID postingId, String coverLetterText);
    List<ApplicationSummaryDto> findApplicationsForCandidate(String candidateEmail);
    List<ApplicationDetailsDto> findApplicationsForPosting(UUID postingId);
}
