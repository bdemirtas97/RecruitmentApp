package com.recruitment.app.infrastructure.web;

import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.port.in.ApplicationUseCasePort;
import com.recruitment.app.domain.port.in.EmployeeUseCasePort;
import com.recruitment.app.domain.port.in.PostingUseCasePort;
import com.recruitment.app.infrastructure.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/postings")
@RequiredArgsConstructor
public class PostingController {

    private final PostingUseCasePort postingUseCasePort;
    private final EmployeeUseCasePort employeeUseCasePort;
    private final ApplicationUseCasePort applicationUseCasePort;

    @GetMapping
    public String showPostingsList(Model model, Principal principal) {
        List<PostingSummary> postings = postingUseCasePort.findPostingsForRecruiter(principal.getName())
                .stream()
                .map(PostingSummary::fromDomain)
                .collect(Collectors.toList());
        model.addAttribute("postings", postings);
        return "list-postings";
    }

    @GetMapping("/new")
    public String showCreatePostingForm(Model model) {
        List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers().stream()
                .map(HiringManagerDto::fromDomain)
                .toList();

        model.addAttribute("createPostingRequest", new PostingCreationRequest());
        model.addAttribute("hiringManagers", hiringManagers);

        return "create-posting";
    }

    @PostMapping
    public String processCreatePosting(@Valid @ModelAttribute("createPostingRequest") PostingCreationRequest request,
                                       BindingResult bindingResult,
                                       Principal principal,
                                       Model model) {

        if (bindingResult.hasErrors()) {
            List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers().stream()
                    .map(HiringManagerDto::fromDomain)
                    .toList();
            model.addAttribute("hiringManagers", hiringManagers);
            return "create-posting";
        }

        postingUseCasePort.publishPosting(principal.getName(), request);
        return "redirect:/postings?creationSuccess";
    }

    @GetMapping("/{id}")
    public String showUpdatePostingForm(@PathVariable UUID id, Model model) {
        Posting posting = postingUseCasePort.getPostingById(id);

        List<ApplicationDetailsDto> applications = applicationUseCasePort.findApplicationsForPosting(id)
                .stream()
                .map(ApplicationDetailsDto::fromDomain)
                .toList();

        List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers().stream()
                .map(HiringManagerDto::fromDomain)
                .collect(Collectors.toList());

        model.addAttribute("postingUpdateRequest", PostingUpdate.fromDomain(posting));
        model.addAttribute("hiringManagers", hiringManagers);
        model.addAttribute("applications", applications);
        model.addAttribute("postingId", id);

        return "update-posting";
    }

    @PostMapping("/{id}")
    public String processUpdatePosting(@PathVariable UUID id,
                                       @Valid @ModelAttribute("postingUpdateRequest") PostingUpdate request,
                                       BindingResult bindingResult,
                                       Model model) {

        if (bindingResult.hasErrors()) {
            List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers().stream()
                    .map(HiringManagerDto::fromDomain)
                    .collect(Collectors.toList());
            model.addAttribute("hiringManagers", hiringManagers);
            List<ApplicationDetailsDto> applications = applicationUseCasePort.findApplicationsForPosting(id).stream().map(
                    ApplicationDetailsDto::fromDomain).toList();
            model.addAttribute("applications", applications);
            model.addAttribute("postingId", id);
            return "update-posting";
        }

        postingUseCasePort.updatePosting(id, request);
        return "redirect:/postings/" + id + "?updateSuccess";
    }
}