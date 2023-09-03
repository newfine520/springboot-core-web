package com.isg.springboottest.annotations.Import.UsageThree;

import com.isg.springboottest.annotations.Import.UsageOne.ServiceA;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceB;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceInterface;
import com.isg.springboottest.annotations.Import.UsageTwo.EnableService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@EnableServiceB(name="TestServiceC")
@Configuration
public class ConfigE {
    public ServiceInterface getServiceA()
    {
        return new ServiceA();
    }
}
