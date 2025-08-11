package com.recruitment.app.domain.port.in;

import com.recruitment.app.domain.dto.HiringManagerDto;
import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.dto.EmployeeDetails;
import com.recruitment.app.domain.dto.EmployeeSignupRequest;

import java.util.List;

public interface EmployeeUseCasePort {
    void signup(EmployeeSignupRequest request);
    EmployeeDetails getDetailsByEmail(String name);
    List<HiringManagerDto> findHiringManagers();
}
