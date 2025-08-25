package com.recruitment.app.infrastructure.service;

import com.recruitment.app.domain.model.Posting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
public class PostingImportResult {
    private int totalPostings;
    private Collection<Posting> postings;
}
