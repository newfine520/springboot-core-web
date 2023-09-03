package com.isg.springboottest.annotations.Import.UsageOne;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(ServiceB.class)
@Configuration
public class ConfigA {
    public ServiceInterface getServiceA()
    {
        return new ServiceA();
    }
}
