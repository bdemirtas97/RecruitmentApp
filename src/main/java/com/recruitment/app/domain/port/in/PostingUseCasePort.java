package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.dto.PostingSummary;
import com.recruitment.app.domain.dto.PostingSummaryForCandidate;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.dto.PostingCreationRequest;
import com.recruitment.app.domain.dto.PostingUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    void uploadBulkResume(List<MultipartFile> resumes, UUID postingId) throws IOException;
    void importPostings(String email);
}
