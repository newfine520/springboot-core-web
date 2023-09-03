package com.isg.springboottest.annotations.Import.UsageTwo;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;

public class ServiceImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata)
    {
        //可以是@Configuration注解修饰的类，也可以是具体的Bean类的全限定名
        //return new String[]{"com.isg.springboottest.annotations.Import.UsageTwo.ConfigD"};



        //这里的importingClassMetadata针对的是使用@EnableService的非注解类
        //因为`AnnotationMetadata`是`Import`注解所在的类属性，如果所在类是注解类，则延伸至应用这个注解类的非注解类为止
        Map<String , Object> map = importingClassMetadata.getAnnotationAttributes(EnableService.class.getName(), true);
        String name = (String) map.get("name");
        if (Objects.equals(name, "B")) {
            return new String[]{"com.isg.springboottest.annotations.Import.UsageTwo.ConfigD"};
        }
        return new String[0];
    }
}
