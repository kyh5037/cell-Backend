package com.example.cellproject.controllers;

import com.example.cellproject.dto.UserRequest;
import com.example.cellproject.repositorys.UserRepository;
import com.example.cellproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody UserRequest userRequest) throws Exception {
        return new ResponseEntity<>(userService.register(userRequest), HttpStatus.OK);
    }
}
