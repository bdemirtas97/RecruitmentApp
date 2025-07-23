package com.recruitment.app.infrastructure.persistence.application;

import com.recruitment.app.domain.model.Application;
import com.recruitment.app.infrastructure.persistence.candidate.CandidateMapper;
import com.recruitment.app.infrastructure.persistence.posting.PostingMapper;

public class ApplicationMapper {
    public static ApplicationJpaEntity toEntity(Application domain){
        ApplicationJpaEntity entity = new ApplicationJpaEntity();
        entity.setId(domain.getId());
        entity.setCandidate(CandidateMapper.toJpaEntity(domain.getCandidate()));
        entity.setPosting(PostingMapper.toJpaEntity(domain.getPosting()));
        entity.setCreationDate(domain.getCreationDate());
        entity.setCoverLetterText(domain.getCoverLetterText());
        entity.setScore(domain.getScore());
        entity.setStatus(domain.getStatus());
        return entity;
    }

    public static Application toDomain(ApplicationJpaEntity entity){
        return Application.builder()
                .id(entity.getId())
                .candidate(CandidateMapper.toDomain(entity.getCandidate()))
                .posting(PostingMapper.toDomain(entity.getPosting()))
                .creationDate(entity.getCreationDate())
                .coverLetterText(entity.getCoverLetterText())
                .status(entity.getStatus())
                .score(entity.getScore())
                .build();
    }
}
