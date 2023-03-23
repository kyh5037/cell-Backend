package com.example.cellproject.security.jwt;

import com.example.cellproject.security.SecurityUserDetailsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.spec.KeySpec;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private String salt;

    private Key secretKey;

    private final long exp = 1000L * 60 * 60;

    private final SecurityUserDetailsService securityUserDetailsService;

    @PostConstruct
    protected void init(){

    }
}
