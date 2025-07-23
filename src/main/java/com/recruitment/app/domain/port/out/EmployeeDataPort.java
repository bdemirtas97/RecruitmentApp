package com.recruitment.app.domain.port.out;

import com.recruitment.app.domain.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeDataPort {
    Employee addEmployee(Employee employee);
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Employee> findAllEmployeesByRole(String role);
    Optional<Employee> findById(UUID id);
}
