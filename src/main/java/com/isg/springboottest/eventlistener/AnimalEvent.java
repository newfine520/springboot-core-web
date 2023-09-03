package com.isg.springboottest.eventlistener;

import java.awt.*;
import java.util.EventObject;

public class AnimalEvent extends EventObject {

    public AnimalEvent(Object source)
    {
        super(source);
        Animal animal=(Animal) source;
        System.out.println("event: Generate event —— "+animal.getName()+" is eating!");
    }

}
