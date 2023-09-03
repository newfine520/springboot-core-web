package com.isg.springboottest.common;

import com.isg.springboottest.annotations.ApiIdempotent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodIdempotentCheck implements HandlerInterceptor {
    //20211119token问题用拦截器统一管理



    private Logger logger= LoggerFactory.getLogger(MethodIdempotentCheck.class);

//    @Resource
//    private StringRedisTemplate stringRedisTemplate;

    @Override
    public  boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws  Exception
    {
        if(handler instanceof HandlerMethod)
        {
            HandlerMethod hanlerMethod=(HandlerMethod) handler;
            Method method=hanlerMethod.getMethod();
            Class<?> clazz=method.getClass();
            if(clazz.isAnnotationPresent(ApiIdempotent.class))
            {
                if(!checkToken(request))
                {
                    failure(response);
                    return  false;
                }
            }
            else
            {
               if(method.isAnnotationPresent(ApiIdempotent.class))
               {
                   if(!checkToken(request))
                   {
                       failure(response);
                       return  false;
                   }
               }
            }
        }
        return  true;
    }
    public void postHandler(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView)
    {

    }
    private void failure(HttpServletResponse response) throws Exception
    {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":-1,\"message\":\"重复提交\"}");
    }
    private boolean checkToken(HttpServletRequest request)
    {
        logger.info("验证token");
        String token=request.getParameter("access-token");
        if(token==null||token.length()==0)
        {
            token=request.getHeader("access-token");
        }
        logger.info("获取token:{0}",token);
        if(token==null||token.length()==0) {
         return false;
        }
        //boolean exists=stringRedisTemplate.hasKey(token);
        boolean exists=true;
        if(!exists)
        {
            return  false;
        }
        //return  stringRedisTemplate.delete(token);
        return true;
    }
}
