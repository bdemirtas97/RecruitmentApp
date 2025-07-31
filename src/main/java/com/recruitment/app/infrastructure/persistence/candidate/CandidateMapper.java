package com.recruitment.app.infrastructure.persistence.candidate;

import com.recruitment.app.domain.model.Candidate;
import org.springframework.stereotype.Component;

public class CandidateMapper {
    public static CandidateJpaEntity toJpaEntity(Candidate domain) {
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
        entity.setSoftSkills(domain.getSoftSkills());
        entity.setTechSkills(domain.getTechSkills());
        entity.setEmbedding(domain.getEmbedding());
        return entity;
    }

    public static Candidate toDomain(CandidateJpaEntity entity) {
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
                .softSkills(entity.getSoftSkills())
                .techSkills(entity.getTechSkills())
                .embedding(entity.getEmbedding())
                .build();
    }
}