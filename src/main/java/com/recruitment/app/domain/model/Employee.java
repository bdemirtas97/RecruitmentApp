package com.recruitment.app.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Employee {
    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final String firstName;
    private final String lastName;
    private final String role; // "ROLE_RECRUITER" or "ROLE_HIRING_MANAGER"

    public static Employee create(String email, String passwordHash, String firstName, String lastName, String role) {
        return new Employee(UUID.randomUUID(), email, passwordHash, firstName, lastName, role);
    }
}