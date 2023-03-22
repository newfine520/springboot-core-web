package com.isg.springboottest.dao;

import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.UserRequest;

public interface UserDao {
    User get(Integer id);
    Integer deleteById(Integer id);
    Integer update(User user);
    Integer insert(UserRequest user) throws  Exception;
}
