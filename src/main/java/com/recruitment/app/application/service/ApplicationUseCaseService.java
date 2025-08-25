package com.recruitment.app.application.service;

import com.recruitment.app.domain.dto.ApplicationDetailsDto;
import com.recruitment.app.domain.dto.ApplicationSummaryDto;
import com.recruitment.app.domain.model.Application;
import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.port.in.ApplicationUseCasePort;
import com.recruitment.app.domain.port.out.ApplicationDataPort;
import com.recruitment.app.domain.port.out.CandidateDataPort;
import com.recruitment.app.domain.port.out.PostingDataPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ApplicationUseCaseService implements ApplicationUseCasePort {
    private final CandidateDataPort candidateDataPort;
    private final PostingDataPort postingDataPort;
    private final ApplicationDataPort applicationDataPort;

    @Override
    @Transactional
    public void applyForPosting(String candidateEmail, UUID postingId, String coverLetterText) {
        Candidate candidate = candidateDataPort.findByEmail(candidateEmail)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + candidateEmail));

        Posting posting = postingDataPort.findById(postingId)
                .orElseThrow(() -> new EntityNotFoundException("Posting not found: " + postingId));

        if(applicationDataPort.existsByCandidateAndPostingId(candidate.getId(), posting.getId())){
            throw new IllegalArgumentException("You have already applied for this posting.");
        }

        String score = applicationDataPort.calculateSimilarityScore(candidate.getId(), postingId);

        Application application = Application.builder()
                .id(UUID.randomUUID())
                .candidate(candidate)
                .posting(posting)
                .creationDate(LocalDateTime.now())
                .status("APPLIED")
                .coverLetterText(coverLetterText)
                .score(score)
                .build();

        applicationDataPort.addApplication(application);
    }

    @Override
    public List<ApplicationSummaryDto> findApplicationsForCandidate(String candidateEmail) {
        UUID candidateId = candidateDataPort.findByEmail(candidateEmail)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + candidateEmail)).getId();
        return applicationDataPort.findByCandidateId(candidateId).stream().map(ApplicationSummaryDto::fromDomain).toList();
    }

    @Override
    public List<ApplicationDetailsDto> findApplicationsForPosting(UUID postingId) {
        return applicationDataPort.findByPostingId(postingId).stream().map(ApplicationDetailsDto::fromDomain).toList();
    }
}
