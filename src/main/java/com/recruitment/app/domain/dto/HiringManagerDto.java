package com.recruitment.app.domain.dto;

import com.recruitment.app.domain.model.Employee;
import lombok.Value;
import java.util.UUID;

@Value
public class HiringManagerDto {
    UUID id;
    String fullName;

    public static HiringManagerDto fromDomain(Employee employee) {
        return new HiringManagerDto(employee.getId(), employee.getFirstName() + " " + employee.getLastName());
    }
}