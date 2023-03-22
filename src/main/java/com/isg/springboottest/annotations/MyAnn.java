package com.isg.springboottest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//注解的作用目标是：方法
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnn {
    String value() default "";
    public final double pi=3.14;

}
