package com.isg.springboottest;

import com.isg.springboottest.common.Locker;
import com.isg.springboottest.vo.request.UserRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScans;
import sun.jvm.hotspot.utilities.BitMap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.isg.springboottest.mapper")
public class SpringboottestApplication implements CommandLineRunner {



    public  static volatile boolean stop=false;

    private static final ThreadLocal<UserRequest> threadLocal=new ThreadLocal<>();

    Logger logger= LoggerFactory.getLogger(SpringboottestApplication.class);

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) throws InterruptedException {


//        Thread thread=new Thread(()->{
//            int i=0;
//            while(!stop)
//            {
//                i++;
//                System.out.println(i);
//            }
//        });
//        thread.start();
//        System.out.println("begin start thread");
//        Thread.sleep(1000);
//        stop=true;


        SpringApplication.run(SpringboottestApplication.class, args);
    }

    @Override
    public  void run(String...args) throws Exception
    {
        System.out.println("-----服务启动成功-----");
        showConnection();
    }
    private void showConnection() throws SQLException
    {
        logger.info("dataSource:{}", dataSource.getClass().getName());
        Connection connection = dataSource.getConnection();
        logger.info("connection:{}", connection.toString());
    }

}
