package com.recruitment.app.infrastructure.web;

import com.recruitment.app.domain.port.in.EmployeeUseCasePort;
import com.recruitment.app.infrastructure.web.dto.EmployeeDetails;
import com.recruitment.app.infrastructure.web.dto.EmployeeSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeUseCasePort employeeUseCasePort;

    @GetMapping("/signup/employee")
    public String showSignupForm(Model model){
        model.addAttribute("signupRequest", new EmployeeSignupRequest());
        return "signup-employee";
    }

    @PostMapping("/signup/employee")
    public String processSignup(@Valid @ModelAttribute("signupRequest") EmployeeSignupRequest request,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup-employee";
        }

        employeeUseCasePort.signup(request);
        return "redirect:/login/employee?signupSuccess";
    }

    @GetMapping("/employee/profile")
    public String showProfile(Model model, Principal principal) {
        EmployeeDetails details = employeeUseCasePort.getDetailsByEmail(principal.getName());
        model.addAttribute("employeeDetails", details);
        return "profile-employee";
    }
}