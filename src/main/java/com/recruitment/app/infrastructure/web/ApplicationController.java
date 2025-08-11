package com.recruitment.app.infrastructure.web;

import com.recruitment.app.domain.port.in.ApplicationUseCasePort;
import com.recruitment.app.domain.dto.ApplicationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationUseCasePort applicationUseCasePort;

    @PostMapping("/candidate/postings/{id}/apply")
    public String processApplication(@PathVariable("id") UUID postingId,
                                     @Valid @ModelAttribute("applicationRequest") ApplicationRequest request,
                                     BindingResult bindingResult,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.applicationRequest", bindingResult);
            redirectAttributes.addFlashAttribute("applicationRequest", request);
            return "redirect:/candidate/postings/" + postingId;
        }

        try {
            applicationUseCasePort.applyForPosting(principal.getName(), postingId, request.getCoverLetterText());
            return "redirect:/candidate/applications?success";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("applyError", e.getMessage());
            return "redirect:/candidate/postings/" + postingId;
        }
    }
}