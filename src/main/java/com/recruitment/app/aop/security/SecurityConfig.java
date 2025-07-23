package com.recruitment.app.aop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Qualifier("candidateUserDetailsService")
    private final UserDetailsService candidateUserDetailsService;

    @Qualifier("employeeUserDetailsService")
    private final UserDetailsService employeeUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain candidateFilterChain(HttpSecurity http) throws Exception {
        var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(candidateUserDetailsService).passwordEncoder(passwordEncoder());

        http
                .securityMatcher("/candidate/**", "/login/candidate")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login/candidate").permitAll()
                        .anyRequest().hasAuthority("CANDIDATE")
                )
                .formLogin(form -> form
                        .loginPage("/login/candidate")
                        .loginProcessingUrl("/login/candidate")
                        .defaultSuccessUrl("/candidate/profile")
                        .failureUrl("/login/candidate?error=true")
                );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain employeeFilterChain(HttpSecurity http) throws Exception {
        var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(employeeUserDetailsService).passwordEncoder(passwordEncoder());

        http
                .securityMatcher("/employee/**", "/postings/**", "/login/employee")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login/employee").permitAll()
                        .anyRequest().hasAnyAuthority("RECRUITER", "HIRING_MANAGER")
                )
                .formLogin(form -> form
                        .loginPage("/login/employee")
                        .loginProcessingUrl("/login/employee")
                        .defaultSuccessUrl("/employee/profile")
                        .failureUrl("/login/employee?error=true")
                );
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/signup/**", "/error/**", "/favicon.ico", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout")
                        .permitAll()
                );
        return http.build();
    }
}