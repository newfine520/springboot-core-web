package com.isg.springboottest.annotations.Import.UsageTwo;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;



@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(DefferredServiceImportSelector.class)
@interface EnableDefferredService {
    String name();
}
