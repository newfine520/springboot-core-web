package com.isg.springboottest.controller;

import com.isg.springboottest.annotations.PackResponse;
import com.isg.springboottest.pojo.Activity;
import com.isg.springboottest.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @PackResponse
    @RequestMapping("/list")
    public Object listActivity(@RequestParam("storeId")String storeId)
    {
        return  activityService.listActivity(storeId);
    }
}
