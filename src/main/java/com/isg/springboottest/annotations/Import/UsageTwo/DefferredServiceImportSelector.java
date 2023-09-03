package com.isg.springboottest.annotations.Import.UsageTwo;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;

//实现DeferredImportSelector接口，这样selectImports返回的类都是最后加载的，而不是像@Import注解那样，先加载。
public class DefferredServiceImportSelector implements DeferredImportSelector {
  @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata)
    {
        Map<String,Object> map=annotationMetadata.getAnnotationAttributes(EnableDefferredService.class.getName(),true);
        String name=(String)map.get("name");
        if(Objects.equals(name,"B"))
        {
            return new String[]{"com.isg.springboottest.annotations.Import.UsageTwo.ConfigD"};
        }
           return  new String[0];
    }
}
