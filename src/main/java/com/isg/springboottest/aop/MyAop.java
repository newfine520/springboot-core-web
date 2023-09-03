package com.isg.springboottest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Date;

@Aspect//标注为切面类
@Component//标注为ioc管理
public class MyAop {

    @Around(value="@annotation(com.isg.springboottest.annotations.MyAnn)")
    public Object log1(ProceedingJoinPoint pjp) throws Throwable{
        //获取日志对象
        Logger logger= LoggerFactory.getLogger(MyAop.class);

        //获取方法名和参数
        Object proceed=pjp.proceed();
        if(proceed!=null)//目标方法成功，则记录日志
        {
            String name=pjp.getSignature().getName();//目标方法参数
            Object[] args=pjp.getArgs();//目标方法参数
            String argsStr= Arrays.toString(args);
            logger.info("在这个时间："+new Date()+"执行了："+name+"方法，传递的参数："+argsStr);
        }
        return  proceed;
    }
}
