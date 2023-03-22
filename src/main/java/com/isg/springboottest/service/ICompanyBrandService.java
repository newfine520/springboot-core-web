package com.isg.springboottest.service;

import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.CombrandRequest;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.MyResponseEntity;
import com.isg.springboottest.vo.response.UserResponse;
import com.isg.springboottest.vo.response.UserResponseForPage;

import java.util.List;

public interface ICompanyBrandService {
  MyResponseEntity insertOrUpdateCompanyBrand(CombrandRequest combrandRequest);
}
