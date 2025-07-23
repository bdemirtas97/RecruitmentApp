package com.recruitment.app.aop.security;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.out.CandidateDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("candidateUserDetailsService")
@RequiredArgsConstructor
public class CandidateUserDetailsService implements UserDetailsService {

    private final CandidateDataPort candidateDataPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Candidate candidate = candidateDataPort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Candidate not found with email: " + email));

        var authorities = Collections.singletonList(new SimpleGrantedAuthority("CANDIDATE"));

        return new User(candidate.getEmail(), candidate.getPasswordHash(), authorities);
    }
}