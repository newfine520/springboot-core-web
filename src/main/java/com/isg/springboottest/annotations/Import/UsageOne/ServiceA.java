package com.isg.springboottest.annotations.Import.UsageOne;

public class ServiceA implements ServiceInterface{
    @Override
    public void test() {
       System.out.println("ServiceA");
    }

}
