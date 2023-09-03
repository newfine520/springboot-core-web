package com.isg.springboottest.eventlistener.springeventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListenerTwo {
    @EventListener
    public void onEvent(MyApplicationEvent event)
    {
        System.out.println("收到事件："+event);
    }
}
