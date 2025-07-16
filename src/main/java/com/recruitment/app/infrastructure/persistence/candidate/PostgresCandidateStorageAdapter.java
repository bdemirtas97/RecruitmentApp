package com.recruitment.app.infrastructure.persistence.candidate;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.out.CandidateStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

interface SpringDataCandidateRepository extends JpaRepository<CandidateJpaEntity, UUID> {
    Optional<CandidateJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

@Repository
@RequiredArgsConstructor
public class PostgresCandidateStorageAdapter implements CandidateStoragePort {
    private final SpringDataCandidateRepository jpaRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public Candidate add(Candidate candidate) {
        CandidateJpaEntity entity = candidateMapper.toJpaEntity(candidate);
        return candidateMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Candidate> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(candidateMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}