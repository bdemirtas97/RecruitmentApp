package com.recruitment.app.infrastructure.persistence.application;

import com.recruitment.app.domain.model.Application;
import com.recruitment.app.domain.port.out.ApplicationDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface SpringDataApplicationRepository extends JpaRepository<ApplicationJpaEntity, UUID> {
    @Query(
            value = "SELECT 1 - (c.embedding <=> p.embedding) FROM candidates c, postings p WHERE c.id = :candidateId AND p.id = :postingId",
            nativeQuery = true
    )
    Double calculateSimilarityScore(@Param("candidateId") UUID candidateId, @Param("postingId") UUID postingId);
    boolean existsByCandidateIdAndPostingId(UUID candidateId, UUID postingId);
    @Query("SELECT a FROM ApplicationJpaEntity a JOIN FETCH a.posting WHERE a.candidate.id = :candidateId")
    List<ApplicationJpaEntity> findAllByCandidateId(UUID candidateId);
    @Query("SELECT a FROM ApplicationJpaEntity a JOIN FETCH a.candidate WHERE a.posting.id = :postingId")
    List<ApplicationJpaEntity> findAllByPostingId(UUID postingId);
}

@Repository
@RequiredArgsConstructor
public class PostgresApplicationDataAdapter implements ApplicationDataPort {
    private final SpringDataApplicationRepository jpaRepository;

    public Optional<Application> findById(UUID id){
        return jpaRepository.findById(id).map(ApplicationMapper::toDomain);
    }


    @Override
    public Application addApplication(Application application) {
        ApplicationJpaEntity entity = ApplicationMapper.toEntity(application);
        return ApplicationMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public boolean existsByCandidateAndPostingId(UUID candidateId, UUID postingId) {
        return jpaRepository.existsByCandidateIdAndPostingId(candidateId, postingId);
    }

    @Override
    public List<Application> findByCandidateId(UUID candidateId) {
        return jpaRepository.findAllByCandidateId(candidateId).stream().map(ApplicationMapper::toDomain).toList();
    }

    @Override
    public List<Application> findByPostingId(UUID postingId) {
        return jpaRepository.findAllByPostingId(postingId).stream().map(ApplicationMapper::toDomain).toList();
    }

    @Override
    public String calculateSimilarityScore(UUID candidateId, UUID postingId) {
        return "%.2f".formatted(jpaRepository.calculateSimilarityScore(candidateId, postingId) * 100);
    }
}
