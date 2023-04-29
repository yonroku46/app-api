package com.app.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MUserDto {

    private String userId;

    private String userPw;

    private String userMail;

    private String userName;

    private String corpFlg;

    private Date latestLogin;
}
