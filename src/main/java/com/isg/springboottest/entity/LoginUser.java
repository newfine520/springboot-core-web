package com.isg.springboottest.entity;

import lombok.Data;

import java.util.List;

@Data
public class LoginUser {
    private String userId;
    private Boolean superUser;
    private List<Roles> auth;
}
