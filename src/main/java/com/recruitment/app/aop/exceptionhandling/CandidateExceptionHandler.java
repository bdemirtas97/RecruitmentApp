package com.recruitment.app.aop.exceptionhandling;

import com.recruitment.app.application.service.CandidateUseCaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.security.Principal;

@ControllerAdvice
@RequiredArgsConstructor
public class CandidateExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CandidateExceptionHandler.class);
    private final CandidateUseCaseService candidateUseCaseService;


    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleSignupException(UserAlreadyExistsException ex, Model model) {
        logger.error("Error signing up the user: {}", ex.getRequestDto().getEmail());
        model.addAttribute("signupRequest", ex.getRequestDto());
        model.addAttribute("errorMessage", ex.getMessage());
        return "signup-candidate";
    }

    @ExceptionHandler(ResumeUploadException.class)
    public String handleUploadException(ResumeUploadException ex, Model model, Principal principal) {
        logger.error("Error  updating the user: {}", principal.getName());
        logger.error(ex.getMessage());
        logger.error(ex.getS3Message());
        model.addAttribute("profileDetails", candidateUseCaseService.getDetailsByEmail(principal.getName()));
        model.addAttribute("profileUpdate", ex.getRequestDto());
        model.addAttribute("errorMessage", ex.getMessage());
        return "profile-candidate";
    }

    @ExceptionHandler(AIServiceResumeException.class)
    public String handleFetchingResumeException(AIServiceResumeException ex, Model model, Principal principal) {
        logger.error("Error  updating the user: {}", principal.getName());
        logger.error(ex.getMessage());
        logger.error(ex.getWebClientMessage());
        model.addAttribute("profileDetails", candidateUseCaseService.getDetailsByEmail(principal.getName()));
        model.addAttribute("profileUpdate", ex.getRequestDto());
        model.addAttribute("errorMessage", ex.getMessage());
        return "profile-candidate";
    }
}
