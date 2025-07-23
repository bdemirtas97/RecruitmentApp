package com.recruitment.app.infrastructure.persistence.application;

import com.recruitment.app.domain.model.Application;
import com.recruitment.app.domain.port.out.ApplicationDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

interface SpringDataApplicationRepository extends JpaRepository<ApplicationJpaEntity, UUID> {
    boolean existsByCandidateIdAndPostingId(UUID candidateId, UUID postingId);
    List<ApplicationJpaEntity> findAllByCandidateId(UUID candidateId);
}

@Repository
@RequiredArgsConstructor
public class PostgresApplicationDataAdapter implements ApplicationDataPort {
    private final SpringDataApplicationRepository jpaRepository;


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
}
