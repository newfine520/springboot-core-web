package com.isg.springboottest.eventlistener.springeventlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
    @Override
    public void onApplicationEvent(MyApplicationEvent myApplicationEvent) {
        System.out.println("收到事件："+myApplicationEvent);
    }

}
