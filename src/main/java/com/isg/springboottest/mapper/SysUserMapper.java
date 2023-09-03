package com.isg.springboottest.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isg.springboottest.entity.SysUser;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.UserRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("sfa_henkel")
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> listUser();

}
