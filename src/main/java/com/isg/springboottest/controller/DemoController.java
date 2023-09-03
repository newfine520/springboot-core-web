package com.isg.springboottest.controller;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSON;
import com.isg.springboottest.annotations.PackResponse;
import com.isg.springboottest.entity.*;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.FileService;
import com.isg.springboottest.service.UserService;
import com.isg.springboottest.service.YqStatService;
import com.isg.springboottest.vo.request.DownloadFileRequestParam;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.MyResponseEntity;
import com.isg.springboottest.vo.response.UserResponse;
import com.isg.springboottest.vo.response.ViewResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.isg.springboottest.annotations.ApiIdempotent;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

//import com.alibaba.fastjson;
@RestController
@RequestMapping("/demo")
@Api(tags="springboot学习工程相关接口")
public class DemoController {
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    Logger logger= LoggerFactory.getLogger(DemoController.class);
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private YqStatService yqStatService;

    @Autowired
    private DataSource dataSource;
//    @ApiOperation("查询用户。。。")
//    @GetMapping("/create")
//    public String create()
//    {
//        String accesstoken= UUID.randomUUID().toString().replace("--","");
//        //stringRedisTemplate.opsForValue().set(accesstoken,accesstoken,10, TimeUnit.MINUTES);
//        stringRedisTemplate.opsForValue().set("TOKEN-IDAP2","\""+"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJJREFQMiIsImV4cCI6MTY1ODIyODA1NiwianRpIjoiMTIzNDU2In0.UDHPtuCA9byGmGXjuZ0MjG00Jwb8zRWTE4_sAnepifdZB42xqtiiTY6hbZ5TacQO4lDEBv3HIiYqEE0OuUtdWQ"+"\"",120, TimeUnit.MINUTES);
//        LoginUser loginUser=new LoginUser();
//        loginUser.setUserId("IDAP2");
//        loginUser.setSuperUser(true);
//        String loginUserStr= JSON.toJSONString(loginUser);
//        stringRedisTemplate.opsForValue().set("LOGINUSER-IDAP2",loginUserStr,120, TimeUnit.MINUTES);
//        return  accesstoken;
//    }
    @GetMapping("/test")
    public Object test()
    {
        List<Animal> animalList=new ArrayList<>();
        Animal animalOne=new Animal();
        animalOne.setYear(2021);
        animalOne.setMonth(1);
        animalOne.setValue("100");
        animalList.add(animalOne);

        Animal animalTwo=new Animal();
        animalTwo.setYear(2021);
        animalTwo.setMonth(2);
        animalTwo.setValue("200");
        animalList.add(animalTwo);

        Animal animalThree=new Animal();
        animalThree.setYear(2021);
        animalThree.setMonth(1);
        animalThree.setValue("100");
        animalList.add(animalThree);

        List<Result> results=new ArrayList<>();
        Map<Integer,List<Animal>> collect=animalList.stream().collect(Collectors.groupingBy(Animal::getYear));
        for (Integer key:collect.keySet())
        {
            Result result=new Result();
            result.setYear(key);
            result.setValues(collect.get(key));
            results.add(result);
        }
        System.out.println(results);
        List<ResponseResult> responseResults=new ArrayList<>();
        responseResults=(List<ResponseResult>)JSON.parse(results.toString());
        return responseResults;
    }
    @GetMapping("/hi")
    public String hiResttemplate(){
        return restTemplate.getForObject("http://gateway-service/yqbigdataweb/demo/create",String.class);
    }
    @GetMapping("/batchDownload")
    public void batchDownloadFiles(DownloadFileRequestParam params, String userId,HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        fileService.batchDownloadFiles(params,userId,request,response);
    }

    @PostMapping("/getStatExcel")
    public void getStatExcel(@RequestParam("file")MultipartFile file) throws ExecutionException, InterruptedException {
        yqStatService.getStatExcel(file);
    }
    @PostMapping("/save")
    @ApiIdempotent
    public  Object business()
    {
//        Map<String,Object> result=new Map<String,Object>();
//        result.put("code",0);
//        result.put("message","创建成功");
        return "{\"code\":0,\"message\":\"创建成功\"}";
    }
    @GetMapping("/getUserById")
    public Object getUserById() {
       return userService.get(1);
        //return "hello";
    }
    @GetMapping("/deleteById")
    public Object deleteById() throws Exception
    {
        return userService.deleteById(2);
        //return "hello";
    }
    @GetMapping("/" +
            "" +
            "" +
            "" +
            "" +
            "")
    public Object toJsonString()
    {
        return null;
        // {"code":20000,"msg":"","data":""}
        // {"code":-1,"msg":"发生错误","data":""}
        // {"code":20000,"msg":"","data":{"name":"李四","age":12,"sex":true,"birthday":"2019-09-05"}}
    }
    public  static <E> List<E> str2list(E... args)
    {
        List<E> list = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            list.add(args[i]);
        }
        return list;
    }
    @GetMapping("/str2list")
    public Object str2list() {
       return DemoController.str2list("a","b","d");
       //return DemoController.str2list(1,3,1,4);
    }
    @GetMapping("/listchild")
    public Object listchild() {
        List<Child> list = new ArrayList<>();
        Child child1 = new Child();
        child1.setName("zhangsan");
        child1.setAge(13);
        list.add(child1);
        Child child2 = new Child();
        child2.setName("李四");
        child2.setAge(20);
        list.add(child2);
        return DemoController.listchild(list);
    }
    public static List<String> listchild(List<? extends Parent> childs) {
        List<String> list=new ArrayList();
        childs.forEach(child -> list.add(child.getName()));
        return list;
    }
    @PackResponse
    @PostMapping("/insertWithOutRollBack")
    public MyResponseEntity<Integer> insertWithOutRollBack(@Valid @RequestBody User userRequest) throws  Exception
    {

        logger.info("输入参数userRequest.RealName:"+userRequest.getRealName());
        logger.info("输入参数userRequest:"+userRequest);
        userRequest.setUID(1);
        userRequest.setRealName("张三");
        MyResponseEntity<Integer> myResponseEntity;
        try {
            Integer flag=userService.insertWithoutRollBack(userRequest);
            myResponseEntity = MyResponseEntity.success(flag);
        }
        catch (Exception ex)
        {
            logger.info("异常信息："+ex.getMessage());
            myResponseEntity = MyResponseEntity.error(ex.getMessage());
        }
        logger.info("输出参数myResponseEntity:"+myResponseEntity);
        return myResponseEntity;
    }
    @PackResponse
    @PostMapping("/insertWithRollBack")
    public MyResponseEntity<Integer> insertWithRollBack(@Valid @RequestBody User userRequest) throws  Exception
    {
        logger.info("输入参数userRequest.RealName:"+userRequest.getRealName());
        logger.info("输入参数userRequest:"+userRequest);
        userRequest.setUID(27);
        //userRequest.setRealName("张三");
        MyResponseEntity<Integer> myResponseEntity;
        try {
            Integer flag=userService.insertWithRollBack(userRequest);
            myResponseEntity = MyResponseEntity.success(flag);
        }
        catch (Exception ex)
        {
            logger.info("异常信息："+ex.getMessage());
            myResponseEntity = MyResponseEntity.error(ex.getMessage());
        }
        logger.info("输出参数myResponseEntity:"+myResponseEntity);
        return myResponseEntity;
    }
    @GetMapping("/GetInfo")
    public List<String> GetParaFromInterface(String projectIds)
    {
        String strProjectIds=projectIds;
        String[] str=strProjectIds.split(",");
        List<String> strList=Arrays.asList(str);
        return strList;
    }
//    @GetMapping("/queryTerm")
//    public Object queryTerm() throws IOException {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.termQuery("name.keyword","张无忌"));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        return response;
//    }
//    @GetMapping("/setRedis")
//    public String setRedis()
//    {
//        stringRedisTemplate.opsForValue().set("TOKEN-IDAP2","\""+"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJJREFQMiIsImV4cCI6MTY1ODIyODA1NiwianRpIjoiMTIzNDU2In0.UDHPtuCA9byGmGXjuZ0MjG00Jwb8zRWTE4_sAnepifdZB42xqtiiTY6hbZ5TacQO4lDEBv3HIiYqEE0OuUtdWQ"+"\"",120, TimeUnit.MINUTES);
//        return "";
//    }
//    @GetMapping("/getRedis")
//    public String getRedis()
//    {
//        String tokenStr=stringRedisTemplate.opsForValue().get("TOKEN-IDAP2");
//        return tokenStr;
//    }
    @RequestMapping("/instances")
    public List<ServiceInstance> instance()
    {
        return discoveryClient.getInstances("opinion-web");
    }


    @PostMapping("/list")
    public List<UserResponse> getUserList(@Valid @RequestBody UserRequest userRequest)
    {
        return userService.listUser(userRequest);
    }

    @GetMapping("/debug")
    public void debug()
    {
        logger.debug("debug");
    }

}
