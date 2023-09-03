package com.isg.springboottest.test;

import com.alibaba.fastjson.JSONObject;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.UserService;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
public class UserTest {
//    @Autowired
//    private RestHighLevelClient client;
    @Autowired
    private UserService service;
    @Test
    public  void deleteById() throws Exception
    {
        service.deleteById(2);
    }
    @Test
    public  void update()
    {
        User user=service.get(4);
        user.setRealName("八戒");
        service.update(user);
    }
//    @Test
//    public void queryTerm() throws IOException{
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.termQuery("name.keyword","张无忌"));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
}
