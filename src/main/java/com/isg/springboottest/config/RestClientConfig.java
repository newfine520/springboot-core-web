package com.isg.springboottest.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RestClientConfig {
    @Value("${elasticsearch.clientIps}")
   private String clientIps;
    @Value("${elasticsearch.httpPort}")
    private int httpPort;



    private HttpHost[] getHttpHosts(String clientIps, int esHttpPort)
    {
        String[] clientIpList=clientIps.split(",");
        HttpHost[] httpHosts=new HttpHost[clientIpList.length];
        for(int i=0;i<clientIpList.length;i++)
        {
            httpHosts[i]=new HttpHost(clientIpList[i],esHttpPort,"http");
        }
        return httpHosts;
    }
//    @Bean
//    public RestHighLevelClient restHighLevelClient()
//    {
//        CredentialsProvider credentialsProvider=new BasicCredentialsProvider();
//        //credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(userName,password));
//        return new RestHighLevelClient(RestClient.builder(getHttpHosts(clientIps,httpPort)).setHttpClientConfigCallback((HttpAsyncClientBuilder httpAsyncClientBuilder)->httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
//    }
}
