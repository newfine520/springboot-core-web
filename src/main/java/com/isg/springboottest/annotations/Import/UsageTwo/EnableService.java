package com.isg.springboottest.annotations.Import.UsageTwo;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * 指定实现ImportSelector的类，通过AnnotationMetadata里面的属性，动态加载类。AnnotationMetadata是Import注解所在的类属性（如果所在类是注解类，则延伸至应用这个注解类的非注解类为止）。
 *
 * 需要实现selectImports方法，返回要加载的@Configuation或者具体Bean类的全限定名的String数组。
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(ServiceImportSelector.class)
public @interface EnableService {
    String name();
}
