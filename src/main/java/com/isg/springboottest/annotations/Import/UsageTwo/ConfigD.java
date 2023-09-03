package com.isg.springboottest.annotations.Import.UsageTwo;

import com.isg.springboottest.annotations.Import.UsageOne.ServiceB;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigD {
    @Bean
    @ConditionalOnMissingBean
    public ServiceInterface getServiceB()
    {
        return new ServiceB();
    }
}
