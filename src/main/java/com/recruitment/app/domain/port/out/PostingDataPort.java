package com.recruitment.app.domain.port.out;

import com.recruitment.app.domain.model.Posting;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostingDataPort {
    Posting addPosting(Posting posting);
    List<Posting> findPostingsByIdForRecruiter(UUID id);
    List<Posting> findPostingsByIdForHiringManager(UUID id);
    Optional<Posting> findById(UUID postingId);
    List<Posting> findAllByStatus(String status);
    void addAll(Collection<Posting> postings);
}
