package com.recruitment.app.infrastructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }
    @GetMapping("/login/candidate")
    public String showCandidateLoginForm() {
        return "login-candidate";
    }
    @GetMapping("/login/employee")
    public String showEmployeeLoginForm() {
        return "login-employee";
    }
}