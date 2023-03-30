package com.example.cellproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest {

    private Long usrNo;

    private String usrId;

    private String usrPwd;

    private String name;

    private String nickname;
}
