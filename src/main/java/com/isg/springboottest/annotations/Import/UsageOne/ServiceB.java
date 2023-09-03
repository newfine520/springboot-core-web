package com.isg.springboottest.annotations.Import.UsageOne;

public class ServiceB implements ServiceInterface{
    @Override
    public void test()
    {
        System.out.println("ServiceB");
    }
}
