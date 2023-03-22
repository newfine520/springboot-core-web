package com.isg.springboottest.service.impl;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.isg.springboottest.mapper.ActivityMapper;
import com.isg.springboottest.pojo.Activity;
import com.isg.springboottest.service.ActivityService;
import com.isg.springboottest.vo.response.ActivityResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.update;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    //@NonNull 注解，添加在方法参数、类属性上，用于自动生成 null 参数检查。若确实是 null 时，抛出 NullPointerException 异常。
    @Override
    public List<ActivityResponse> listActivity(@NotNull String storeId)
    {
        LambdaUpdateWrapper<Activity> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(Activity::getActivityName,"2023年2月21日JAVA培训");
        updateWrapper.eq(Activity::getActivityNo,storeId);
        update(updateWrapper);

        List<Activity> activities = activityMapper.listActivity(storeId);
        if (CollectionUtils.isEmpty(activities)) {
            return Lists.newArrayList();
        }
        List<ActivityResponse> list = activities.stream().map(a -> {
            ActivityResponse response = new ActivityResponse();
            BeanUtils.copyProperties(a, response);
            return response;
        }).collect(Collectors.toList());
        return list;
    }
}
