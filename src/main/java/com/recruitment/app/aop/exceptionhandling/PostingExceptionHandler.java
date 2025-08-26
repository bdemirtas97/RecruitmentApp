package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.domain.dto.*;
import com.recruitment.app.domain.port.in.ApplicationUseCasePort;
import com.recruitment.app.domain.port.in.CandidateUseCasePort;
import com.recruitment.app.domain.port.in.EmployeeUseCasePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.security.Principal;
import java.util.List;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class PostingExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(PostingExceptionHandler.class);
    private final CandidateUseCasePort candidateUseCasePort;
    private final ApplicationUseCasePort applicationUseCasePort;
    private final EmployeeUseCasePort employeeUseCasePort;

    @ExceptionHandler(AIServicePostingCreationException.class)
    public String handleAIServicePostingCreationException(AIServicePostingCreationException ex, Model model){
        logger.error(ex.getMessage());
        logger.error(ex.getWebClientMessage());
        List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers();
        model.addAttribute("createPostingRequest", ex.getRequestDto());
        model.addAttribute("hiringManagers", hiringManagers);
        model.addAttribute("errorMessage", ex.getMessage());
        return "create-posting";
    }

    @ExceptionHandler(AIServicePostingUpdateException.class)
    public String handlePostingUpdateException(AIServicePostingUpdateException ex, Model model, Principal principal){
        logger.error(ex.getMessage());
        logger.error(ex.getWebClientMessage());
        List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers();
        List<ApplicationDetailsDto> applications = applicationUseCasePort.findApplicationsForPosting(ex.getPostingId());
        List<CandidateSearchResult> bestMatches = candidateUseCasePort.findBestCandidatesForPosting(ex.getPostingId());
        model.addAttribute("hiringManagers", hiringManagers);
        model.addAttribute("bestMatches", bestMatches);
        model.addAttribute("applications", applications);
        model.addAttribute("postingId", ex.getPostingId());
        model.addAttribute("postingUpdateRequest", ex.getRequestDto());
        model.addAttribute("errorMessage", ex.getMessage());
        return "update-posting";
    }

    @ExceptionHandler(AIServiceMatchAnalyzeException.class)
    public ResponseEntity<?> handleMatchAnalyzeException(AIServiceMatchAnalyzeException ex){
        logger.error(ex.getMessage());
        logger.error(ex.getResponseBody());
        return new ResponseEntity<>(ex.getStatus());
    }

}
