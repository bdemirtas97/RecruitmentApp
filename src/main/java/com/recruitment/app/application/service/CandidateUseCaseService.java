package com.recruitment.app.application.service;

import com.recruitment.app.aop.exceptionhandling.ResumeUploadException;
import com.recruitment.app.aop.exceptionhandling.UserAlreadyExistsException;
import com.recruitment.app.domain.dto.*;
import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.in.CandidateUseCasePort;
import com.recruitment.app.domain.port.out.CandidateDataPort;
import com.recruitment.app.domain.port.out.FileStoragePort;
import com.recruitment.app.infrastructure.service.RecruitmentAIClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import com.recruitment.app.aop.exceptionhandling.AIServiceResumeException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class CandidateUseCaseService implements CandidateUseCasePort {

    private final CandidateDataPort candidateDataPort;
    private final PasswordEncoder passwordEncoder;
    private final FileStoragePort fileStoragePort;
    private final RecruitmentAIClient aiClient;

    @Override
    @Transactional
    public void signup(CandidateSignupRequest request) {
        if (candidateDataPort.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("A candidate with this email already exists.", request);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        Candidate candidate = Candidate.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .passwordHash(hashedPassword)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role("CANDIDATE")
                .build();

        candidateDataPort.addCandidate(candidate);
    }

    @Override
    @Transactional
    public void updateProfile(String email, CandidateProfileUpdate request, InputStream fileStream, String originalFileName) throws ResumeUploadException {
        Candidate candidate = candidateDataPort.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + email));
        String fileUrl = null;
        if(fileStream != null){
            String fileName = "%s_%s".formatted(UUID.randomUUID(), originalFileName);
            try{
                fileUrl = fileStoragePort.addResume(fileName, fileStream);
            }
            catch (Exception ex){
                throw new ResumeUploadException("Resume file could not uploaded", ex.getMessage(), request);
            }
        }
        if(fileUrl != null){
            try{
                ResumeParsingResponse parsingResponse = aiClient.fetchParsedResume(fileUrl);
                candidate.updateResumeUrl(fileUrl);
                candidate.updateProfile(request);
                candidate.setSkills(String.join(", ", parsingResponse.getSoft_skills()),
                        String.join(", ", parsingResponse.getTech_skills()));
                candidate.setEmbedding(parsingResponse.getParsed_cv_vector());
                candidate.setParsedCv(parsingResponse.getParsed_cv_text());
            }
            catch(WebClientResponseException ex){
                throw new AIServiceResumeException("Resume file couldn't be analyzed!", ex.getMessage(), ex.getResponseBodyAsString(), request);
            }
        }
        candidateDataPort.addCandidate(candidate);
    }

    @Override
    public CandidateDetails getDetailsByEmail(String email) {
        return candidateDataPort.findByEmail(email)
                .map(CandidateDetails::fromDomain)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + email));
    }

    @Override
    public List<CandidateSearchResult> findBestCandidatesForPosting(UUID postingId) {
        return candidateDataPort.findBestCandidatesByPostingId(postingId);
    }
}