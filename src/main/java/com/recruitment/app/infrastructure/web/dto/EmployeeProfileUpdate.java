package com.recruitment.app.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeProfileUpdate {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Email
    private String contactEmail;
    private String contactPhone;
}
