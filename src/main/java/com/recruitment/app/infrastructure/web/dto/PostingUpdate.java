package com.recruitment.app.infrastructure.web.dto;

import com.recruitment.app.domain.model.Posting;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostingUpdate {
    @NotBlank private String title;
    @NotBlank private String location;
    @NotBlank private String level;
    @NotBlank private String workingType;
    @NotBlank private String workPlace;
    @NotBlank private String details;
    @NotBlank private String status;
    @NotBlank private String keywords;

    public static PostingUpdate fromDomain(Posting posting) {
        PostingUpdate dto = new PostingUpdate();
        dto.setTitle(posting.getTitle());
        dto.setDetails(posting.getDetails());
        dto.setLocation(posting.getLocation());
        dto.setLevel(posting.getLevel());
        dto.setStatus(posting.getStatus());
        dto.setWorkingType(posting.getWorkingType());
        dto.setWorkPlace(posting.getWorkPlace());
        dto.setKeywords(posting.getKeywords());
        return dto;
    }
}