package com.recruitment.app.application.service;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.in.CandidateUseCasePort;
import com.recruitment.app.domain.port.out.CandidateStoragePort;
import com.recruitment.app.domain.port.out.FileStoragePort;
import com.recruitment.app.infrastructure.web.dto.CandidateDetails;
import com.recruitment.app.infrastructure.web.dto.CandidateProfileUpdate;
import com.recruitment.app.infrastructure.web.dto.CandidateSignupRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateApplicationServicePort implements CandidateUseCasePort {

    private final CandidateStoragePort candidateStoragePort;
    private final PasswordEncoder passwordEncoder;
    private final FileStoragePort fileStoragePort;

    @Override
    @Transactional
    public void signup(CandidateSignupRequest request) {
        if (candidateStoragePort.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("A candidate with this email already exists.");
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
        candidateStoragePort.add(candidate);
    }

    @Override
    @Transactional
    public void updateProfile(String email, CandidateProfileUpdate request, InputStream fileStream, String originalFileName) throws IOException {
        Candidate candidate = candidateStoragePort.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + email));
        String fileUrl = null;
        if(fileStream != null){
            String fileName = "%s_%s".formatted(UUID.randomUUID(), originalFileName);
            fileUrl = fileStoragePort.addResume(fileName, fileStream);

        }
        candidate.updateProfile(request.getFirstName(), request.getLastName(), request.getContactEmail(), request.getContactPhone());
        if(fileUrl != null){
            candidate.updateResumeUrl(fileUrl);
        }
        candidateStoragePort.add(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateDetails getDetailsByEmail(String email) {
        return candidateStoragePort.findByEmail(email)
                .map(CandidateDetails::fromDomain)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + email));
    }
}