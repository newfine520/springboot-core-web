package com.isg.springboottest.annotations.Import.UsageThree;

import com.isg.springboottest.annotations.Import.UsageTwo.EnableService;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ServiceImportBeanDefinitionRegistarar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    //public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
           {
               Map<String, Object> map = importingClassMetadata.getAnnotationAttributes(EnableServiceB.class.getName(), true);
               String name = (String) map.get("name");
               BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ServiceC.class)
                       //增加构造参数
                       .addConstructorArgValue(name);
               //注册Bean
               registry.registerBeanDefinition("serviceC", beanDefinitionBuilder.getBeanDefinition());
           }
       }
}
