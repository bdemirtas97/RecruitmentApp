package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.model.Application;
import java.util.List;
import java.util.UUID;

public interface ApplicationUseCasePort {
    void applyForPosting(String candidateEmail, UUID postingId, String coverLetterText);
    List<Application> findApplicationsForCandidate(String candidateEmail);
    List<Application> findApplicationsForPosting(UUID postingId);
}
