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
        entity.setParsedCv(domain.getParsedCv());
        entity.setEmbedding(domain.getEmbedding());
        entity.setResumeCareerField(domain.getResumeCareerField());
        entity.setResumeDepartment(domain.getResumeDepartment());
        entity.setApplicationCareerFields(domain.getApplicationCareerFields());
        entity.setApplicationDepartments(domain.getApplicationDepartments());
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
                .parsedCv(entity.getParsedCv())
                .embedding(entity.getEmbedding())
                .resumeCareerField(entity.getResumeCareerField())
                .resumeDepartment(entity.getResumeDepartment())
                .applicationCareerFields(entity.getApplicationCareerFields())
                .applicationDepartments(entity.getApplicationDepartments())
                .build();
    }
}