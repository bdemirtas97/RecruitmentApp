package com.recruitment.app.infrastructure.persistence.candidate;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.out.CandidateDataPort;
import com.recruitment.app.domain.dto.CandidateSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface SpringDataCandidateRepository extends JpaRepository<CandidateJpaEntity, UUID> {
    @Query(value = """
    SELECT 
        c.id as "candidateId",
        p.id as "postingId",
        concat(c.firstname, ' ', lastname) as "candidateFullName",
        c.contactemail as "candidateEmail",
        c.contactphone as "candidatePhone",
        c.softskills as "softSkills",
        c.techskills as "techSkills",
        c.fileurl as "resumeUrl",
        to_char((1 - (p.embedding <=> c.embedding)) * 100, '00.00') AS score
    FROM candidates c, postings p
    WHERE p.id = :postingId AND
    (c.applicationdepartments ILIKE '%' || p.department || '%' OR c.resumedepartment ILIKE '%' || p.department || '%')
    ORDER BY score DESC
    """, nativeQuery = true)
    List<CandidateSearchResult> findBestCandidatesByPostingId(@Param("postingId") UUID postingId);
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

    @Override
    public List<CandidateSearchResult> findBestCandidatesByPostingId(UUID postingId) {
        return jpaRepository.findBestCandidatesByPostingId(postingId);
    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        return jpaRepository.findById(id).map(CandidateMapper::toDomain);
    }
}