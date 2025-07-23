package com.recruitment.app.infrastructure.persistence.application;

import com.recruitment.app.infrastructure.persistence.candidate.CandidateJpaEntity;
import com.recruitment.app.infrastructure.persistence.posting.PostingJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name ="applications")
public class ApplicationJpaEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateId")
    private CandidateJpaEntity candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postingId")
    private PostingJpaEntity posting;

    @Column(nullable=false)
    private LocalDateTime creationDate;

    private String CoverLetterText;

    @Column(nullable=false)
    private String status;

    private String score;
}
