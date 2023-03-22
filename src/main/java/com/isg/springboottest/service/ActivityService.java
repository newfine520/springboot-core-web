package com.isg.springboottest.service;

import com.isg.springboottest.pojo.Activity;
import com.isg.springboottest.vo.response.ActivityResponse;

import java.util.List;

public interface ActivityService {
    List<ActivityResponse> listActivity(String storeId);
}
