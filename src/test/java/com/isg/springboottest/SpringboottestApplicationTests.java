package com.isg.springboottest;
import com.alibaba.fastjson.JSONObject;
import com.isg.springboottest.service.UserService;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.UserService;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.AggregationBuilder;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringboottestApplicationTests {
//    @Autowired
//    private RestHighLevelClient client;
//    @Autowired
//    private UserService userService;
    @Test
    public void contextLoads() throws IOException {
//        User user=userService.get(4);
//        if(user!=null)
//        {
//        user.setName("八戒");
//        userService.update(user);
//        userService.deleteById(2);
//        }
//        countQuery();

    }
    //等值查询-term
//    private void queryTerm() throws IOException {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.termQuery("name.keyword","张无忌"));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //多值查询-terms
//    private void queryTerms() throws IOException
//    {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.termsQuery("sect.keyword", Arrays.asList("明教","峨眉派")));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //范围查询-range
//    private void queryRange()throws  IOException
//    {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gte(15).lte(18));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //前缀查询-prefix
//    private void queryPrefix()throws  IOException
//    {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.prefixQuery("sect.keyword", "峨眉"));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //通配符查询-wildcard
//    private void queryWildcard()throws  IOException
//    {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.wildcardQuery("name.keyword", "张*忌"));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //复合查询
//    private void queryMust()throws  IOException
//    {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("sex","男"))
//                .must(QueryBuilders.termQuery("sect.keyword","明教")));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //布尔查询
//    private void queryShould() throws IOException {
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建查询语句
//        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("sex","女"))
//                        .must(QueryBuilders.rangeQuery("age").gte(10).lte(40))
//                        .should(QueryBuilders.termQuery("sect.keyword","明教"))
//                .should(QueryBuilders.termQuery("address.keyword","峨眉山"))
//                .should(QueryBuilders.termQuery("sect.keyword","明教"))
//                .minimumShouldMatch(1));
//        System.out.println("searchSourceBuilder==============================="+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    //聚合查询
//    private void maxQuery() throws IOException {
//        AggregationBuilder aggregationBuilder= AggregationBuilders.max("max_age").field("age");
//        SearchRequest searchRequest=new SearchRequest("school");
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        searchSourceBuilder.aggregation(aggregationBuilder);
//        System.out.println("searchSourceBuilder======================"+searchSourceBuilder);
//
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest,RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
//    private void countQuery() throws IOException{
//        SearchRequest searchRequest=new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        searchSourceBuilder.size(0);
//        AggregationBuilder aggregationBuilder=AggregationBuilders.terms("sect_count").field("sect.keyword");
//        searchSourceBuilder.aggregation(aggregationBuilder);
//        System.out.println("searchSourceBuilder======================"+searchSourceBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response=client.search(searchRequest,RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//    }
}
