package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.infrastructure.web.dto.PostingCreationRequest;
import com.recruitment.app.infrastructure.web.dto.PostingUpdate;

import java.util.List;
import java.util.UUID;

public interface PostingUseCasePort {
    void publishPosting(String email, PostingCreationRequest request);
    List<Posting> findPostingsForRecruiter(String email);
    Posting getPostingById(UUID postingId);
    void updatePosting(UUID postingId, PostingUpdate request);
    List<Posting> findAllActivePostings();
}
