package com.recruitment.app.domain.model;

import com.recruitment.app.infrastructure.web.dto.PostingUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Posting {
    private UUID id;
    private String title;
    private String location;
    private String level;
    private String workingType;
    private String workPlace;
    private String details;
    private String status;
    private Employee recruiter;
    private Employee hiringManager;
    private String keywords;


    public void updatePosting(PostingUpdate request){
        this.title = request.getTitle();
        this.location = request.getLocation();
        this.level = request.getLevel();
        this.workingType = request.getWorkingType();
        this.workPlace = request.getWorkPlace();
        this.details = request.getDetails();
        this.status = request.getStatus();
        this.keywords =request.getKeywords();
    }
}
