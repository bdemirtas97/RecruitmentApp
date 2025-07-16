package com.recruitment.app.aop.security;

import com.recruitment.app.domain.model.Candidate;
import com.recruitment.app.domain.port.out.CandidateStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service("candidateUserDetailsService")
@RequiredArgsConstructor
public class CandidateUserDetailsService implements UserDetailsService {
    private final CandidateStoragePort candidateStoragePort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Candidate candidate = candidateStoragePort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Candidate not found with email: " + email));
        return new User(candidate.getEmail(), candidate.getPasswordHash(), Collections.singletonList(new SimpleGrantedAuthority(candidate.getRole())));
    }
}