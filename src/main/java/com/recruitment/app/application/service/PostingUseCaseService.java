package com.recruitment.app.application.service;

import com.recruitment.app.domain.dto.*;
import com.recruitment.app.domain.model.Application;
import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.port.in.ApplicationUseCasePort;
import com.recruitment.app.domain.port.in.PostingUseCasePort;
import com.recruitment.app.domain.port.out.*;
import com.recruitment.app.infrastructure.service.PostingImportResult;
import com.recruitment.app.infrastructure.service.PostingImporterService;
import com.recruitment.app.infrastructure.service.RecruitmentAIClient;
import com.recruitment.app.utils.PostingStringfier;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostingUseCaseService implements PostingUseCasePort {
    private final EmployeeDataPort employeeDataPort;
    private final PostingDataPort postingDataPort;
    private final ApplicationDataPort applicationDataPort;
    private final CandidateDataPort candidateDataPort;
    private final RecruitmentAIClient aiClient;
    private final FileStoragePort fileStoragePort;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUseCasePort applicationUseCasePort;
    private final PostingImporterService postingImporterService;

    @Override
    @Transactional
    public void publishPosting(String email, PostingCreationRequest request) {
        Employee recruiter = employeeDataPort.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + email));
        Employee hiringManager = employeeDataPort.findById(request.getHiringManagerId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + email));


        Posting posting = Posting.builder()
                .id(UUID.randomUUID())
                .title(request.getTitle())
                .location(request.getLocation())
                .level(request.getLevel())
                .workingType(request.getWorkingType())
                .workPlace(request.getWorkPlace())
                .details(request.getDetails())
                .keywords(request.getKeywords())
                .status("ACTIVE") // enum kullan
                .recruiter(recruiter)
                .hiringManager(hiringManager)
                .build();

        //try {
            posting.setEmbedding(aiClient.fetchPostingVector(PostingStringfier.fieldsToString(posting)).getPosting_vector());
        //}
        //catch (WebClientResponseException ex){
        //    throw AIServicePostingException
        //}

        postingDataPort.addPosting(posting);
    }

    @Override
    public List<PostingSummary> findPostingsForRecruiter(String email) {
        UUID recruiterId = employeeDataPort.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + email)).getId();
        return postingDataPort.findPostingsByIdForRecruiter(recruiterId).stream().map(PostingSummary::fromDomain).toList();
    }

    @Override
    public Posting getPostingById(UUID postingId) {
        return postingDataPort.findById(postingId)
                .orElseThrow(() -> new EntityNotFoundException("Posting not found: " + postingId));
    }

    @Override
    @Transactional
    public void updatePosting(UUID postingId, PostingUpdate request) {
        Posting posting = postingDataPort.findById(postingId)
                .orElseThrow(() -> new EntityNotFoundException("Posting not found: " + postingId));
        posting.updatePosting(request);
        posting.setEmbedding(aiClient.fetchPostingVector(PostingStringfier.fieldsToString(posting)).getPosting_vector());
        postingDataPort.addPosting(posting);
    }

    @Override
    public List<PostingSummaryForCandidate> findAllActivePostings() {
        return postingDataPort.findAllByStatus("ACTIVE").stream().map(PostingSummaryForCandidate::fromDomain).toList();
    }

    @Override
    public String fetchMatchAnalyzeForPosting(UUID applicationId) {
        Application application = applicationDataPort.findById(applicationId).orElseThrow(() -> new EntityNotFoundException("Application not found: " + applicationId));
        Posting posting = postingDataPort.findById(application.getPosting().getId()).orElseThrow(() -> new EntityNotFoundException("Posting not found: " + application.getPosting().getId()));
        Candidate candidate = candidateDataPort.findById(application.getCandidate().getId()).orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + application.getCandidate().getId()));
        MatchAnalyzeResponse response = aiClient.fetchAnalyzeResult(candidate.getParsedCv(), PostingStringfier.fieldsToString(posting));
        return response.getResult();
    }

    @Override
    public String fetchMatchAnalyzeForBestCandidate(UUID postingId, UUID candidateId) {
        Posting posting = postingDataPort.findById(postingId).orElseThrow(() -> new EntityNotFoundException("Posting not found: " + postingId));
        Candidate candidate = candidateDataPort.findById(candidateId).orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + candidateId));
        MatchAnalyzeResponse response = aiClient.fetchAnalyzeResult(candidate.getParsedCv(), PostingStringfier.fieldsToString(posting));
        return response.getResult();
    }

    @Override
    @Transactional
    public void uploadBulkResume(List<MultipartFile> resumes, UUID postingId) throws IOException {
        for(MultipartFile resume : resumes){
            Candidate candidate = Candidate.builder()
                    .id(UUID.randomUUID())
                    .firstName("Harun")
                    .lastName("Altı")
                    .email("harun@harun.com")
                    .contactPhone("5371231231")
                    .contactEmail("harun@harun.com")
                    .role("CANDIDATE")
                    .passwordHash(passwordEncoder.encode("12345678"))
                    .build();


            String fileName = "%s_%s".formatted(UUID.randomUUID(), resume.getOriginalFilename());
            String fileUrl = fileStoragePort.addResume(fileName, resume.getInputStream());

            if(fileUrl != null){
                ResumeParsingResponse parsingResponse = aiClient.fetchParsedResume(fileUrl);
                candidate.updateResumeUrl(fileUrl);
                candidate.setSkills(String.join(", ", parsingResponse.getSoft_skills()),
                        String.join(", ", parsingResponse.getTech_skills()));
                candidate.setEmbedding(parsingResponse.getParsed_cv_vector());
                candidate.setParsedCv(parsingResponse.getParsed_cv_text());
            }
            candidateDataPort.addCandidate(candidate);
            applicationUseCasePort.applyForPosting(candidate.getEmail(), postingId, "");
        }
    }

    @Override
    @Transactional
    public void importPostings(String email) {
        Employee recruiter = employeeDataPort.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + email));
        PostingImportResult result = postingImporterService.fetchPostings();
        for(Posting posting : result.getPostings()){
            posting.setRecruiter(recruiter);
            posting.setHiringManager(recruiter);
            posting.setEmbedding(aiClient.fetchPostingVector(PostingStringfier.fieldsToString(posting)).getPosting_vector());
            postingDataPort.addPosting(posting);
        }
    }
}
