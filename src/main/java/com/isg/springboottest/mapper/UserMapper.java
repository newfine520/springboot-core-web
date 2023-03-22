package com.isg.springboottest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isg.springboottest.pojo.Activity;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.UserRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> listUser(@Param("userRequest") UserRequest userRequest);
    List<User> getListForPage(@Param("userRequest") UserRequest userRequest);
}
