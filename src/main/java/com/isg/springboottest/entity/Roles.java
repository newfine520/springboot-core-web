package com.isg.springboottest.entity;

import lombok.Data;

import java.util.List;

@Data
public class Roles {
    private Integer appId;
    private Boolean appUser;
    private List<String> uriList;
}
