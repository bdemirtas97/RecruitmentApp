package com.recruitment.app.infrastructure.persistence.posting;

import com.recruitment.app.infrastructure.persistence.VectorType;
import com.recruitment.app.infrastructure.persistence.employee.EmployeeJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table(name = "postings")
@Getter
@Setter
public class PostingJpaEntity {
    @Id
    private UUID id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String location;

    @Column(nullable=false)
    private String level;

    @Column(nullable=false)
    private String workPlace;

    @Column(nullable=false)
    private String workingType;

    @Column(nullable=false, length=5000)
    private String details;

    @Column(nullable=false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "recruiterId")
    private EmployeeJpaEntity recruiter;

    @ManyToOne
    @JoinColumn(name = "hiringManagerId")
    private EmployeeJpaEntity hiringManager;

    @Column(length=1000)
    private String keywords;

    @Column(name = "embedding", columnDefinition = "vector(768)")
    @Type(VectorType.class)
    private float[] embedding;
}
