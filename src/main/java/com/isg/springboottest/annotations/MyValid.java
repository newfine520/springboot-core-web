package com.isg.springboottest.annotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyValid {
    String message() default  "校验失败";
    Class<? extends Payload>[] payload() default {};
    Class<? extends MyValidHandler> handler();
}
