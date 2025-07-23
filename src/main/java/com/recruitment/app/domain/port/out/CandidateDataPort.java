package com.recruitment.app.domain.port.out;

import com.recruitment.app.domain.model.Candidate;
import java.util.Optional;

public interface CandidateDataPort {
    Candidate addCandidate(Candidate candidate);
    Optional<Candidate> findByEmail(String email);
    boolean existsByEmail(String email);
}