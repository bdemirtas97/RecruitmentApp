package com.recruitment.app.infrastructure.persistence.candidate;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.out.CandidateDataPort;
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
public class PostgresCandidateDataAdapter implements CandidateDataPort {
    private final SpringDataCandidateRepository jpaRepository;

    @Override
    public Candidate addCandidate(Candidate candidate) {
        CandidateJpaEntity entity = CandidateMapper.toJpaEntity(candidate);
        return CandidateMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Candidate> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(CandidateMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}