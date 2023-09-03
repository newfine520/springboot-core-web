package com.isg.springboottest.controller;

import com.isg.springboottest.annotations.PackResponse;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.ActivityService;
import com.isg.springboottest.service.SysUserService;
import com.isg.springboottest.vo.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @PostMapping("/list")
    public Object listSysUser(@Valid @RequestBody UserRequest userRequest)
    {
        return  sysUserService.selectAll(userRequest);
    }
}
