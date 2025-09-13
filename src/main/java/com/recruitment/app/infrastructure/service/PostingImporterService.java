package com.recruitment.app.infrastructure.service;

import com.recruitment.app.domain.model.Posting;

public interface PostingImporterService {
    PostingImportResult fetchPostings();
    Posting fetchPosting(String url);
}