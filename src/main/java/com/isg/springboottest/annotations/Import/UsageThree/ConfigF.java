package com.isg.springboottest.annotations.Import.UsageThree;

import com.isg.springboottest.annotations.Import.UsageOne.ServiceB;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableServiceB(name="TestServiceC")
@Configuration
public class ConfigF {

    /**
     * 并且根据后面的源代码解析可以知道，ImportBeanDefinitionRegistrar 在 @Bean 注解之后加载，所以要修改ConfigA去掉其中被@ConditionalOnMissingBean注解的Bean，否则一定会生成ConfigA的ServiceInterface
     */
//    @Bean
//    @ConditionalOnMissingBean
//    public ServiceInterface getServiceB()
//    {
//        return new ServiceB();
//    }
}
