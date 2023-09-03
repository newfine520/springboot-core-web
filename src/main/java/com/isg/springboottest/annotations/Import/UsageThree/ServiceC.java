package com.isg.springboottest.annotations.Import.UsageThree;

import com.isg.springboottest.annotations.Import.UsageOne.ServiceInterface;

public class ServiceC implements ServiceInterface {
    private final String name;
    ServiceC(String name)
    {
        this.name=name;
    }
    @Override
    public void test()
    {
        System.out.println(name);
    }
}

