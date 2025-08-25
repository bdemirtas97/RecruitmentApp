package com.recruitment.app.infrastructure.web;

import com.recruitment.app.domain.dto.*;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.port.in.ApplicationUseCasePort;
import com.recruitment.app.domain.port.in.CandidateUseCasePort;
import com.recruitment.app.domain.port.in.EmployeeUseCasePort;
import com.recruitment.app.domain.port.in.PostingUseCasePort;
import com.recruitment.app.infrastructure.service.PostingImporterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/postings")
@RequiredArgsConstructor
public class PostingController {

    private final PostingUseCasePort postingUseCasePort;
    private final EmployeeUseCasePort employeeUseCasePort;
    private final ApplicationUseCasePort applicationUseCasePort;
    private final CandidateUseCasePort candidateUseCasePort;
    private final PostingImporterService postingImporterService;

    @GetMapping
    public String showPostingsList(Model model, Principal principal) {
        List<PostingSummary> postings = postingUseCasePort.findPostingsForRecruiter(principal.getName());
        model.addAttribute("postings", postings);
        return "list-postings";
    }

    @GetMapping("/new")
    public String showCreatePostingForm(Model model) {
        List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers();
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
            List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers();
            model.addAttribute("hiringManagers", hiringManagers);
            return "create-posting";
        }

        postingUseCasePort.publishPosting(principal.getName(), request);
        return "redirect:/postings?creationSuccess";
    }

    @GetMapping("/{id}")
    public String showUpdatePostingForm(@PathVariable UUID id, Model model) {
        Posting posting = postingUseCasePort.getPostingById(id);

        List<ApplicationDetailsDto> applications = applicationUseCasePort.findApplicationsForPosting(id);

        List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers();

        List<CandidateSearchResult> bestMatches = candidateUseCasePort.findBestCandidatesForPosting(id);
        model.addAttribute("bestMatches", bestMatches);
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
            List<HiringManagerDto> hiringManagers = employeeUseCasePort.findHiringManagers();
            model.addAttribute("hiringManagers", hiringManagers);
            List<ApplicationDetailsDto> applications = applicationUseCasePort.findApplicationsForPosting(id);
            List<CandidateSearchResult> bestMatches = candidateUseCasePort.findBestCandidatesForPosting(id);
            model.addAttribute("bestMatches", bestMatches);
            model.addAttribute("applications", applications);
            model.addAttribute("postingId", id);
            return "update-posting";
        }

        postingUseCasePort.updatePosting(id, request);
        return "redirect:/postings/" + id + "?updateSuccess";
    }

    @GetMapping("/applications/{id}/match-analyze")
    public ResponseEntity<AnalyzeResponse> analyzeMatch(@PathVariable("id") UUID id){
        return new ResponseEntity<>(new AnalyzeResponse(postingUseCasePort.fetchMatchAnalyzeForPosting(id)), HttpStatus.OK);
    }

    @GetMapping("/{postingId}/candidates/{candidateId}/match-analyze")
    public ResponseEntity<AnalyzeResponse> analyzeMatchForCandidate(@PathVariable("postingId") UUID postingId, @PathVariable("candidateId") UUID candidateId){
        return new ResponseEntity<>(new AnalyzeResponse(postingUseCasePort.fetchMatchAnalyzeForBestCandidate(postingId, candidateId)), HttpStatus.OK);
    }

    @PostMapping("/{id}/bulk-cv")
    public ResponseEntity<?> uploadBulkCv(@PathVariable UUID id, @RequestParam("cvs") List<MultipartFile> cvs) throws IOException{
        postingUseCasePort.uploadBulkResume(cvs, id);
        String successMessage = "Successfully uploaded and processed %d CV(s).".formatted(cvs.size());
        return new ResponseEntity<>(Map.of("message", successMessage), HttpStatus.OK);
    }

    @GetMapping("/posting-imports")
    public ResponseEntity<?> importPostingFromLever(Principal principal){
        postingUseCasePort.importPostings(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}