package com.recruitment.app.infrastructure.web;

import com.recruitment.app.domain.port.in.PostingUseCasePort;
import com.recruitment.app.infrastructure.web.dto.ApplicationRequest;
import com.recruitment.app.infrastructure.web.dto.PostingDetails;
import com.recruitment.app.infrastructure.web.dto.PostingSummaryForCandidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("candidate/postings")
@RequiredArgsConstructor
public class PublicPostingController {

    private final PostingUseCasePort postingUseCase;

    @GetMapping
    public String listActivePostings(Model model) {
        List<PostingSummaryForCandidate> postings = postingUseCase.findAllActivePostings()
                .stream()
                .map(PostingSummaryForCandidate::fromDomain)
                .collect(Collectors.toList());

        model.addAttribute("postings", postings);
        return "list-postings-for-candidate";
    }

    @GetMapping("/{id}")
    public String showPostingDetails(@PathVariable UUID id, Model model) {
        PostingDetails details = PostingDetails.fromDomain(postingUseCase.getPostingById(id));
        model.addAttribute("posting", details);
        model.addAttribute("applicationRequest", new ApplicationRequest());
        model.addAttribute("id", id);
        return "view-posting";
    }
}