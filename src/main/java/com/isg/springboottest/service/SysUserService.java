package com.isg.springboottest.service;

import com.isg.springboottest.entity.SysUser;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.SysUserResponse;

import java.util.List;

public interface SysUserService {

    List<SysUserResponse> selectAll(UserRequest userRequest);


}
