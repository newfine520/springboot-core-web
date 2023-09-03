package com.isg.springboottest.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isg.springboottest.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;
//import org.springframework.data.map.repository.config.EnableMapRepositories;

import java.util.List;
@DS("sfa_henkel")
public interface ActivityMapper extends BaseMapper<Activity>
{
    List<Activity> listActivity(String storeId);
}
