package com.isg.springboottest.eventlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.EventObject;

public class AnimalListener implements EventListener {
    public void eatEventHandler(EventObject eventObject)
    {
        //从事件中获取事件源
        Animal source=(Animal) eventObject.getSource();
        //打印事件源的名称
        System.out.println("listener: Get event —— " + source.getName() + " is eating!");
    }
}
