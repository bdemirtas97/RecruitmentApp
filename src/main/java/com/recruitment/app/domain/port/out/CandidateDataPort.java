package com.recruitment.app.domain.port.out;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.dto.CandidateSearchResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateDataPort {
    Candidate addCandidate(Candidate candidate);
    Optional<Candidate> findByEmail(String email);
    boolean existsByEmail(String email);
    List<CandidateSearchResult> findBestCandidatesByPostingId(UUID postingId);
    Optional<Candidate> findById(UUID id);
}