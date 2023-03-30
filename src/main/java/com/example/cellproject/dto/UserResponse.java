package com.example.cellproject.dto;

import com.example.cellproject.models.Authority;
import com.example.cellproject.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class UserResponse {

    private Long usrNo;

    private String usrId;

    private String name;

    private String nickname;

    private List<Authority> roles = new ArrayList<>();

    private String token;

    public UserResponse(User user) {
        this.usrNo = user.getUsrNo();
        this.usrId = user.getUsrId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.roles = user.getRoles();
    }
}
