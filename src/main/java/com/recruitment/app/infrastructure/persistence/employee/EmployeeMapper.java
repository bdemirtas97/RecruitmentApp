package com.recruitment.app.infrastructure.persistence.employee;

import com.recruitment.app.domain.model.Employee;

public class EmployeeMapper {
    public static EmployeeJpaEntity toJpaEntity(Employee domain) {
        EmployeeJpaEntity entity = new EmployeeJpaEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setRole(domain.getRole());
        return entity;
    }

    public static Employee toDomain(EmployeeJpaEntity entity){
        return Employee.builder()
                .id(entity.getId())
                .passwordHash(entity.getPasswordHash())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .role(entity.getRole())
                .build();
    }
}
