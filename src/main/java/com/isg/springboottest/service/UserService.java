package com.isg.springboottest.service;

import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.UserResponse;
import com.isg.springboottest.vo.response.UserResponseForPage;

import java.util.List;

public interface UserService {
    User get(Integer id);
    Integer deleteById(Integer id);
    Integer update(User user);
    Integer insertWithRollBack(User user) throws  Exception;
    Integer insertWithoutRollBack(User user);
    List<UserResponse> listUser(UserRequest userRequest);
    UserResponseForPage getListForPage(UserRequest userRequest);
}
