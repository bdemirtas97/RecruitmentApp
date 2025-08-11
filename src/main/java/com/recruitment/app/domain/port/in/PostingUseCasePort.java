package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.dto.PostingSummary;
import com.recruitment.app.domain.dto.PostingSummaryForCandidate;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.dto.PostingCreationRequest;
import com.recruitment.app.domain.dto.PostingUpdate;
import java.util.List;
import java.util.UUID;

public interface PostingUseCasePort {
    void publishPosting(String email, PostingCreationRequest request);
    List<PostingSummary> findPostingsForRecruiter(String email);
    Posting getPostingById(UUID postingId);
    void updatePosting(UUID postingId, PostingUpdate request);
    List<PostingSummaryForCandidate> findAllActivePostings();
    String fetchMatchAnalyzeForPosting(UUID applicationId);
    String fetchMatchAnalyzeForBestCandidate(UUID postingId, UUID candidateId);
}
