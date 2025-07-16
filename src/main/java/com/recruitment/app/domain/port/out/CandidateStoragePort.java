package com.recruitment.app.domain.port.out;

import com.recruitment.app.domain.model.Candidate;
import java.util.Optional;

public interface CandidateStoragePort {
    Candidate add(Candidate candidate);
    Optional<Candidate> findByEmail(String email);
    boolean existsByEmail(String email);
}