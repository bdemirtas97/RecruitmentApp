package com.recruitment.app.domain.model;

import lombok.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Employee {
    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final String firstName;
    private final String lastName;
    private final String role;
    private List<Posting> recruiterPostings;
    private List<Posting> hiringManagerPostings;
}