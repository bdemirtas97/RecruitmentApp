package com.recruitment.app.application.service;

import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.port.in.PostingUseCasePort;
import com.recruitment.app.domain.port.out.EmployeeDataPort;
import com.recruitment.app.domain.port.out.PostingDataPort;
import com.recruitment.app.infrastructure.web.dto.PostingCreationRequest;
import com.recruitment.app.infrastructure.web.dto.PostingUpdate;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostingApplicationServicePort implements PostingUseCasePort {
    private EmployeeDataPort employeeDataPort;
    private final PostingDataPort postingDataPort;

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

        postingDataPort.addPosting(posting);
    }

    @Override
    public List<Posting> findPostingsForRecruiter(String email) {
        UUID recruiterId = employeeDataPort.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + email)).getId();
        return postingDataPort.findPostingsByIdForRecruiter(recruiterId);
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
                .orElseThrow(() -> new EntityNotFoundException("Posting not found: " + postingId));;
        posting.updatePosting(request);
        postingDataPort.addPosting(posting);
    }

    @Override
    public List<Posting> findAllActivePostings() {
        return postingDataPort.findAllByStatus("ACTIVE");
    }
}
