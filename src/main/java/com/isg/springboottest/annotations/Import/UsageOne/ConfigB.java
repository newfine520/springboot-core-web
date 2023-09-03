package com.isg.springboottest.annotations.Import.UsageOne;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigB {
    @Bean
    @ConditionalOnMissingBean
    public ServiceInterface getServiceB()
    {
        return new ServiceB();
    }
}
