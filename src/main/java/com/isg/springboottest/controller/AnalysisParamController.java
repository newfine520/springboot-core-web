package com.isg.springboottest.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.UserService;
import com.isg.springboottest.vo.request.DemoObjRequest;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.MyResponseEntity;
import com.isg.springboottest.vo.response.UserResponse;
import com.isg.springboottest.vo.response.UserResponseForPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.*;

@RestController
public class AnalysisParamController {
    @Autowired
    private UserService userService;
    @GetMapping("/analysis")
    public Object GetAnalysisInfo(UserRequest userRequest)
    {
        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
        List<UserResponse> responses=userService.listUser(userRequest);
        if(CollectionUtils.isEmpty(responses))
        {
            response.setCode(-1);
            response.setMsg("失败！");
        }
        else
        {
            response.setData(userService.listUser(userRequest));
            response.setCode(0);
            response.setMsg("成功！");
        }
        return response;
    }
    @GetMapping("/analysisr")
    public Object GetAnalysisInfo(@RequestParam("userIds") List<Integer> userIds)
    {
//        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
//        UserRequest userRequest=new UserRequest();
//        userRequest.setUserIds(userIds);
//        List<UserResponse> responses=userService.listUser(userRequest);
//        if(CollectionUtils.isEmpty(responses))
//        {
//            response.setCode(-1);
//            response.setMsg("失败！");
//        }
//        else
//        {
//            response.setData(userService.listUser(userRequest));
//            response.setCode(0);
//            response.setMsg("成功！");
//        }
        //return response;
        return userIds;
    }
    @GetMapping("/analysisarr")
    public Object GetAnalysisInfo(Integer[] userIds)
    {
        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
        UserRequest userRequest=new UserRequest();
        userRequest.setUserIds(Arrays.asList(userIds));
        List<UserResponse> responses=userService.listUser(userRequest);
        if(CollectionUtils.isEmpty(responses))
        {
            response.setCode(-1);
            response.setMsg("失败！");
        }
        else
        {
            response.setData(userService.listUser(userRequest));
            response.setCode(0);
            response.setMsg("成功！");
        }
        return response;
    }
    @PostMapping("/panalysis")
    public Object GetPAnalysis(@RequestBody List<Integer> userIds)
    {
        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
        UserRequest userRequest=new UserRequest();
        userRequest.setUserIds(userIds);
        List<UserResponse> responses=userService.listUser(userRequest);
        if(CollectionUtils.isEmpty(responses))
        {
            response.setCode(-1);
            response.setMsg("失败！");
        }
        else
        {
            response.setData(userService.listUser(userRequest));
            response.setCode(0);
            response.setMsg("成功！");
        }
        return response;
    }
    @PostMapping("/panalysis2")
    public Object GetPAnalysisInfo(@RequestBody UserRequest userRequest)
    {
        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
        List<UserResponse> responses=userService.listUser(userRequest);
        if(CollectionUtils.isEmpty(responses))
        {
            response.setCode(-1);
            response.setMsg("失败！");
        }
        else
        {
            response.setData(userService.listUser(userRequest));
            response.setCode(0);
            response.setMsg("成功！");
        }
        return response;
    }
    @PostMapping("/panalysis3")
    public Object GetPAnalysisInfo(@RequestBody List<UserRequest> userRequestList)
    {
        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
        List<UserResponse> responses=userService.listUser(userRequestList.get(0));
        if(CollectionUtils.isEmpty(responses))
        {
            response.setCode(-1);
            response.setMsg("失败！");
        }
        else
        {
            response.setData(userService.listUser(userRequestList.get(0)));
            response.setCode(0);
            response.setMsg("成功！");
        }
        return response;
    }
    @GetMapping("/stranalysis3")
    public Object GetPAnalysisInfo(String params)
    {
        MyResponseEntity<List<UserResponse>> response=new MyResponseEntity<List<UserResponse>>();
        UserRequest userRequest=new UserRequest();
        int[] array = Arrays.asList(params.split(",")).stream().mapToInt(Integer::parseInt).toArray();
        Integer[] arr1=new Integer[array.length];
        for(int i=0;i<array.length;i++)
        {
            arr1[i]=array[i];
        }
        //int[] array = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
        userRequest.setUserIds(Arrays.asList(arr1));
        List<UserResponse> responses=userService.listUser(userRequest);
        if(CollectionUtils.isEmpty(responses))
        {
            response.setCode(-1);
            response.setMsg("失败！");
        }
        else
        {
            response.setData(userService.listUser(userRequest));
            response.setCode(0);
            response.setMsg("成功！");
        }
        return response;
    }
    @PostMapping("/panalysisForpage")
    public Object GetPAnalysisInfoForPage(@RequestBody UserRequest userRequest)
    {

        UserResponseForPage userResponseForPage=userService.getListForPage(userRequest);
        return userResponseForPage;
    }
    @PostMapping("/postpanalysis")
    public Object Postpanalysis(@RequestBody UserRequest userRequest)
    {
         return userRequest.getRealName();
    }
//    @GetMapping("/getmappanalysis")
//    public Map<String,Object> GetMappanalysis(@RequestHeader(value="name",required = false,defaultValue = "ready") String name)
//    {
//        Map<String,Object> result=new LinkedHashMap<>();
//        result.put("name",name);
//        return result;
//    }

    @GetMapping("/getmappanalysis")
    public Object GetMappanalysis(@RequestHeader Map<String,String> requestHeaderMap)
    {
        return requestHeaderMap;
    }

    //常规request参数获取，访问路径为/requestParam?id=1
   @RequestMapping(value="/requestParam",produces = "text/plain;charset=UTF-8")//produces可定制返回的response的媒体类型和字符集(json:application/json;charset=UTF-8)
   public String passRequestParam(Long id, HttpServletRequest request)
   {
       return "url:"+request.getRequestURL()+" can access,id:"+id;
   }
   //解释参数到对象，访问路径为/obj?id=1&name=zhangsan(SpringBoot自动做了处理:SpringBoot会帮我们自动填充到实体中)
   @RequestMapping(value="/obj",produces = "application/json;charset=UTF-8")
    public String passObj(DemoObjRequest obj,HttpServletRequest request)
   {
       return "url:"+request.getRequestURL()+" can acess,obj id: "+obj.getId()+"obj name:"+obj.getName();
   }

}
