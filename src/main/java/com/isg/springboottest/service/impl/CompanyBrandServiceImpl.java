package com.isg.springboottest.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.isg.springboottest.dao.UserDao;
import com.isg.springboottest.mapper.CompanyBrandMapper;
import com.isg.springboottest.mapper.UserMapper;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.ICompanyBrandService;
import com.isg.springboottest.service.UserService;
import com.isg.springboottest.vo.request.CombrandRequest;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.MyResponseEntity;
import com.isg.springboottest.vo.response.UserResponse;
import com.isg.springboottest.vo.response.UserResponseForPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyBrandServiceImpl implements ICompanyBrandService {
    @Autowired
    private CompanyBrandMapper companyBrandMapper;
    public MyResponseEntity insertOrUpdateCompanyBrand(CombrandRequest combrandRequest){
        int count = 0;
        if (combrandRequest.getId()==null) {
                count = companyBrandMapper.addCompanyBrand(combrandRequest);
        }else {
           count = companyBrandMapper.updateCompanyBrand(combrandRequest);
        }
        return MyResponseEntity.success(count);
    }
}
