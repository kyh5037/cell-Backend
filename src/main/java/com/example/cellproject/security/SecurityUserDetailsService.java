package com.example.cellproject.security;

import com.example.cellproject.models.User;
import com.example.cellproject.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String usrId) throws UsernameNotFoundException {
        User user = userRepository.findByUsrId(usrId).orElseThrow(
                () -> new UsernameNotFoundException("UsernameNotFoundException : Invalid authentication")
        );

        return new SecurityUserContext(user);
    }
}
