package com.isg.springboottest.annotations.Import.UsageTwo;

import com.isg.springboottest.annotations.Import.UsageOne.ServiceA;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceB;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableService(name="B")
@Configuration
public class ConfigC {
    @Bean
    @ConditionalOnMissingBean
    public ServiceInterface getServiceA()
    {
        return new ServiceA();
    }
}
