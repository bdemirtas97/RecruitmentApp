package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.dto.CandidateDetails;
import com.recruitment.app.domain.dto.CandidateProfileUpdate;
import com.recruitment.app.domain.dto.CandidateSearchResult;
import com.recruitment.app.domain.dto.CandidateSignupRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface CandidateUseCasePort {
    void signup(CandidateSignupRequest request);
    void updateProfile(String email, CandidateProfileUpdate request, InputStream fileStream, String originalFileName) throws IOException;
    CandidateDetails getDetailsByEmail(String email);
    List<CandidateSearchResult> findBestCandidatesForPosting(UUID posting);
}