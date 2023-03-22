package com.isg.springboottest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

public class ApplicationContextUtils {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 静态容器对象
     */
    private static ApplicationContext staticApplicationContext;

    @PostConstruct
    private void init() {
        ApplicationContextUtils.staticApplicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> cls) {
        if (staticApplicationContext != null) {
            return staticApplicationContext.getBean(cls);
        }
        return null;
    }
}
