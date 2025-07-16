package com.recruitment.app.domain.port.in;

import com.recruitment.app.infrastructure.web.dto.CandidateDetails;
import com.recruitment.app.infrastructure.web.dto.CandidateProfileUpdate;
import com.recruitment.app.infrastructure.web.dto.CandidateSignupRequest;

import java.io.IOException;
import java.io.InputStream;

public interface CandidateUseCasePort {
    void signup(CandidateSignupRequest request);
    void updateProfile(String email, CandidateProfileUpdate request, InputStream fileStream, String originalFileName) throws IOException;
    CandidateDetails getDetailsByEmail(String email);
}