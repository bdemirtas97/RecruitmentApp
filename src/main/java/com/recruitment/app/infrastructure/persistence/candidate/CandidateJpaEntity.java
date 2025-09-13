package com.recruitment.app.infrastructure.persistence.candidate;

import com.recruitment.app.infrastructure.persistence.VectorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class CandidateJpaEntity {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String contactEmail;

    private String contactPhone;

    @Column(nullable = false)
    private String role;

    private String fileUrl;

    @Column(length=10000)
    private String softSkills;

    @Column(length=10000)
    private String techSkills;

    @Column(length=100000)
    private String parsedCv;

    private String resumeCareerField;

    private String resumeDepartment;

    private String applicationCareerFields;

    private String applicationDepartments;

    @Column(name = "embedding", columnDefinition = "vector(768)")
    @Type(VectorType.class)
    private float[] embedding;
}