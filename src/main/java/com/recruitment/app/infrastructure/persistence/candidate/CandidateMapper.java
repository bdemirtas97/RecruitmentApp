package com.recruitment.app.infrastructure.persistence.candidate;

import com.recruitment.app.domain.model.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {
    public CandidateJpaEntity toJpaEntity(Candidate domain) {
        CandidateJpaEntity entity = new CandidateJpaEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setRole(domain.getRole());
        entity.setContactEmail(domain.getContactEmail());
        entity.setContactPhone(domain.getContactPhone());
        entity.setFileUrl(domain.getFileUrl());
        return entity;
    }

    public Candidate toDomain(CandidateJpaEntity entity) {
        return Candidate.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .role(entity.getRole())
                .contactEmail(entity.getContactEmail())
                .contactPhone(entity.getContactPhone())
                .fileUrl(entity.getFileUrl())
                .build();
    }
}