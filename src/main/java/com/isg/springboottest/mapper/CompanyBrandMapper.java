package com.isg.springboottest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.vo.request.CombrandRequest;
import com.isg.springboottest.vo.request.UserRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyBrandMapper {
    int addCompanyBrand(@Param("cr") CombrandRequest combrandRequest);
    int updateCompanyBrand(@Param("cr") CombrandRequest combrandRequest);

}
