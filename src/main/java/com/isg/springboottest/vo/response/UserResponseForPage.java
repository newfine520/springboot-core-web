package com.isg.springboottest.vo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class UserResponseForPage {
    public List<UserResponse> responses;
    public Integer page;
    public Integer limit;
    public Long count;
}
