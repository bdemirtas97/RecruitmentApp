package com.recruitment.app.domain.port.out;

import com.recruitment.app.domain.model.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationDataPort {
    Application addApplication(Application application);
    boolean existsByCandidateAndPostingId(UUID candidateId, UUID postingId);
    List<Application> findByCandidateId(UUID candidateId);
}
