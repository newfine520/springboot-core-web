package com.isg.springboottest.config;

import com.isg.springboottest.common.MethodIdempotentCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public  void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(tokenInterceptor());
    }
    @Bean
    public HandlerInterceptor tokenInterceptor(){
        return new MethodIdempotentCheck();
    }
}
