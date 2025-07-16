package com.recruitment.app.infrastructure.web;

import com.recruitment.app.domain.port.in.CandidateUseCasePort;
import com.recruitment.app.infrastructure.web.dto.CandidateDetails;
import com.recruitment.app.infrastructure.web.dto.CandidateProfileUpdate;
import com.recruitment.app.infrastructure.web.dto.CandidateSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateUseCasePort candidateUseCasePort;

    @GetMapping("/signup/candidate")
    public String showSignupForm(Model model) {
        model.addAttribute("signupRequest", new CandidateSignupRequest());
        return "signup-candidate";
    }

    @PostMapping("/signup/candidate")
    public String processSignup(@Valid @ModelAttribute("signupRequest") CandidateSignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup-candidate";
        }
        candidateUseCasePort.signup(request);
        return "redirect:/login/candidate?signupSuccess";
    }

    @GetMapping("/candidate/profile")
    public String showProfile(Model model, Principal principal) {
        CandidateDetails details = candidateUseCasePort.getDetailsByEmail(principal.getName());
        model.addAttribute("profileDetails", details);
        model.addAttribute("profileUpdate", CandidateProfileUpdate.fromDetails(details));
        return "profile-candidate";
    }

    @PostMapping("/candidate/profile")
    public String updateProfile(@Valid @ModelAttribute("profileUpdate") CandidateProfileUpdate request,
                                BindingResult bindingResult,
                                Principal principal, Model model,
                                @RequestParam(value = "resumeFile", required = false) MultipartFile resumeFile) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("profileDetails", candidateUseCasePort.getDetailsByEmail(principal.getName()));
            return "profile-candidate";
        }
        candidateUseCasePort.updateProfile(principal.getName(), request, resumeFile.getInputStream(), resumeFile.getOriginalFilename());
        return "redirect:/candidate/profile?updateSuccess";
    }
}