package com.example.cellproject.service;

import com.example.cellproject.dto.UserRequest;
import com.example.cellproject.models.Authority;
import com.example.cellproject.models.User;
import com.example.cellproject.repositorys.UserRepository;
import com.example.cellproject.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public boolean register(UserRequest userRequest) throws Exception {
        try {
            User user = User.builder()
                    .usrId(userRequest.getUsrId())
                    .usrPw(passwordEncoder.encode(userRequest.getUsrPwd()))
                    .name(userRequest.getName())
                    .nickname(userRequest.getNickname())
                    .build();

            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }
}
