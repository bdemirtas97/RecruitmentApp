package com.recruitment.app.infrastructure.web.dto;

import com.recruitment.app.domain.model.Employee;
import lombok.Value;

@Value
public class EmployeeDetails {
    String email;
    String firstName;
    String lastName;
    String role;

    public static EmployeeDetails fromDomain(Employee employee) {
        return new EmployeeDetails(
                employee.getEmail(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole().replace("ROLE_", "")
        );
    }
}