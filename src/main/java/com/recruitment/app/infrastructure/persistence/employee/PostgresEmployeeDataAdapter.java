package com.recruitment.app.infrastructure.persistence.employee;

import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.port.out.EmployeeDataPort;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface SpringDataEmployeeRepository extends JpaRepository<EmployeeJpaEntity, UUID> {
    Optional<EmployeeJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<EmployeeJpaEntity> findAllByRole(String role);
}

@Repository
@RequiredArgsConstructor
public class PostgresEmployeeDataAdapter implements EmployeeDataPort {
    private final SpringDataEmployeeRepository jpaRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        EmployeeJpaEntity entity = EmployeeMapper.toJpaEntity(employee);
        return EmployeeMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(EmployeeMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public List<Employee> findAllEmployeesByRole(String role) {
        return jpaRepository.findAllByRole(role).stream().map(EmployeeMapper::toDomain).toList();
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return jpaRepository.findById(id).map(EmployeeMapper::toDomain);
    }
}
