package com.recruitment.app.infrastructure.persistence.posting;

import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.infrastructure.persistence.employee.EmployeeMapper;

public class PostingMapper {
    public static Posting toDomain(PostingJpaEntity entity){
        return Posting.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .location(entity.getLocation())
                .level(entity.getLevel())
                .workingType(entity.getWorkingType())
                .workPlace(entity.getWorkPlace())
                .details(entity.getDetails())
                .status(entity.getStatus())
                .keywords(entity.getKeywords())
                .recruiter(EmployeeMapper.toDomain(entity.getRecruiter()))
                .hiringManager(EmployeeMapper.toDomain(entity.getHiringManager()))
                .embedding(entity.getEmbedding())
                .careerField(entity.getCareerField())
                .department(entity.getDepartment())
                .build();
    }

    public static PostingJpaEntity toJpaEntity(Posting domain){
        PostingJpaEntity entity = new PostingJpaEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setLocation(domain.getLocation());
        entity.setLevel(domain.getLevel());
        entity.setWorkingType(domain.getWorkingType());
        entity.setWorkPlace(domain.getWorkPlace());
        entity.setDetails(domain.getDetails());
        entity.setStatus(domain.getStatus());
        entity.setRecruiter(EmployeeMapper.toJpaEntity(domain.getRecruiter()));
        entity.setHiringManager(EmployeeMapper.toJpaEntity(domain.getHiringManager()));
        entity.setKeywords(domain.getKeywords());
        entity.setEmbedding(domain.getEmbedding());
        entity.setCareerField(domain.getCareerField());
        entity.setDepartment(domain.getDepartment());
        return entity;
    }
}
