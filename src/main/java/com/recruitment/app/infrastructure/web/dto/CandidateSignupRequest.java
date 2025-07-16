package com.recruitment.app.infrastructure.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CandidateSignupRequest {
    @NotBlank @Email private String email;
    @NotBlank private String password;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
}