package com.recruitment.app.aop.security;

import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.port.out.EmployeeDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service("employeeUserDetailsService")
@RequiredArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeDataPort employeeDataPort;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeDataPort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + email));

        var authorities = Collections.singletonList(new SimpleGrantedAuthority(employee.getRole()));

        return new User(employee.getEmail(), employee.getPasswordHash(), authorities);
    }
}