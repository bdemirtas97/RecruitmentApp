package com.recruitment.app.application.service;

import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.port.in.EmployeeUseCasePort;
import com.recruitment.app.domain.port.out.EmployeeDataPort;
import com.recruitment.app.infrastructure.web.dto.EmployeeDetails;
import com.recruitment.app.infrastructure.web.dto.EmployeeSignupRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeUseCaseService implements EmployeeUseCasePort {
    private final EmployeeDataPort employeeDataPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(EmployeeSignupRequest request) {
        if (employeeDataPort.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("An Employee with this email already exists.");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .passwordHash(hashedPassword)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .recruiterPostings(new ArrayList<>())
                .hiringManagerPostings(new ArrayList<>())
                .build();

        employeeDataPort.addEmployee(employee);
    }

    @Override
    public EmployeeDetails getDetailsByEmail(String email) {
        return employeeDataPort.findByEmail(email).map(EmployeeDetails::fromDomain).
                orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + email));
    }

    @Override
    public List<Employee> findHiringManagers() {
        return employeeDataPort.findAllEmployeesByRole("HIRING_MANAGER");
    }
}
