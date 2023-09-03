package com.isg.springboottest.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.isg.springboottest.entity.SysUser;
import com.isg.springboottest.mapper.SysUserMapper;
import com.isg.springboottest.mapper.UserMapper;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.SysUserService;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.SysUserResponse;
import com.isg.springboottest.vo.response.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@DS("sfa_henkel")
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    protected SysUserMapper sysUserMapper;
    @Override
    public List<SysUserResponse> selectAll(UserRequest userRequest)
    {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("user_name",userRequest.getRealName());
        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return Lists.newArrayList();
        }
        List<SysUserResponse> list = sysUsers.stream().map(a -> {
            SysUserResponse response = new SysUserResponse();
            BeanUtils.copyProperties(a, response);
            return response;
        }).collect(Collectors.toList());
        return list;
    }

}
