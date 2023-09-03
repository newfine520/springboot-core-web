package com.isg.springboottest;

import com.isg.springboottest.annotations.Import.UsageOne.ConfigA;
import com.isg.springboottest.annotations.Import.UsageOne.ServiceInterface;
import com.isg.springboottest.annotations.Import.UsageThree.ConfigE;
import com.isg.springboottest.annotations.Import.UsageThree.ConfigF;
import com.isg.springboottest.annotations.Import.UsageTwo.ConfigC;
import com.isg.springboottest.annotations.Metadata.MetaDemo;
import com.isg.springboottest.eventlistener.Animal;
import com.isg.springboottest.eventlistener.AnimalListener;
import com.isg.springboottest.eventlistener.springeventlistener.MyApplicationEvent;
import com.isg.springboottest.eventlistener.springeventlistener.MyApplicationListener;
import com.isg.springboottest.eventlistener.springeventlistener.MyApplicationListenerTwo;
import com.isg.springboottest.service.ActivityService;
import com.isg.springboottest.vo.request.UserRequest;
//import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.isg.springboottest.mapper")
public class SpringboottestApplication implements CommandLineRunner {



    public  static volatile boolean stop=false;

    private static final ThreadLocal<UserRequest> threadLocal=new ThreadLocal<>();

    Logger logger= LoggerFactory.getLogger(SpringboottestApplication.class);

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


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


        //20230703 start
//        ApplicationContext context=new AnnotationConfigApplicationContext(ConfigC.class);
//        ServiceInterface bean=context.getBean(ServiceInterface.class);
//        bean.test();
        //20230703 end


        //20230713 metadata start
//        StandardAnnotationMetadata metadata=new StandardAnnotationMetadata(MetaDemo.class,true);
//
//        System.out.println("==============ClassMetadata==========================");
//        ClassMetadata classMetadata=metadata;
//        System.out.println(classMetadata.getClassName());//com.isg.springboottest.annotations.Metadata.MetaDemo
//        System.out.println(classMetadata.getEnclosingClassName());//null
//        System.out.println(StringUtils.arrayToCommaDelimitedString(classMetadata.getMemberClassNames()));//com.isg.springboottest.annotations.Metadata.MetaDemo$InnerClass
//        System.out.println(StringUtils.arrayToCommaDelimitedString(classMetadata.getInterfaceNames()));//java.io.Serializable
//        System.out.println(classMetadata.hasSuperClass());//true
//        System.out.println(classMetadata.getSuperClassName());//java.util.HashMap
//
//        System.out.println(classMetadata.isAnnotation());//false
//        System.out.println(classMetadata.isFinal());//false
//        System.out.println(classMetadata.isIndependent());//true
//
//
//        System.out.println("=======================AnnotatedTypeMetadata================================");
//        AnnotatedTypeMetadata annotatedTypeMetadata=metadata;
//        System.out.println(annotatedTypeMetadata.isAnnotated(Service.class.getName()));//true
//        System.out.println(annotatedTypeMetadata.isAnnotated(Component.class.getName()));//true
//        System.out.println(annotatedTypeMetadata.isAnnotated(EnableAsync.class.getName()));//true
//
//        System.out.println(annotatedTypeMetadata.getAllAnnotationAttributes(Service.class.getName()));//{value=[metaDemo]}
//        System.out.println(annotatedTypeMetadata.getAllAnnotationAttributes(Component.class.getName()));//{value=[componentName, ]}
//        System.out.println(annotatedTypeMetadata.getAllAnnotationAttributes(EnableAsync.class.getName()));//{annotation=[interface java.lang.annotation.Annotation], mode=[PROXY], order=[2147483647], proxyTargetClass=[false]}
//
//
//        System.out.println("=======================AnnotationMetadata================");
//        AnnotationMetadata annotationMetadata=metadata;
//        System.out.println(annotationMetadata.getAnnotationTypes());//[org.springframework.stereotype.Component, org.springframework.stereotype.Service, org.springframework.scheduling.annotation.EnableAsync]
//        System.out.println(annotationMetadata.getMetaAnnotationTypes(Service.class.getName()));//[org.springframework.stereotype.Component, org.springframework.stereotype.Indexed]
//        System.out.println(annotationMetadata.getMetaAnnotationTypes(Component.class.getName()));//[org.springframework.stereotype.Indexed]
//
//        System.out.println(annotationMetadata.hasAnnotation(Service.class.getName()));//true
//        System.out.println(annotationMetadata.hasAnnotation(Component.class.getName()));//true
//
//        System.out.println(annotationMetadata.hasMetaAnnotation(Service.class.getName()));//false
//        System.out.println(annotationMetadata.hasMetaAnnotation(Component.class.getName()));//true
//
//
//        System.out.println(annotationMetadata.hasAnnotatedMethods(Autowired.class.getName()));//true
//        annotationMetadata.getAnnotatedMethods(Autowired.class.getName()).forEach(methodMetadata -> {
//            System.out.println(methodMetadata.getClass());//class org.springframework.core.type.StandardMethodMetadata
//            System.out.println(methodMetadata.getMethodName());//getName
//            System.out.println(methodMetadata.getReturnTypeName());//java.lang.String
//
//
//            CachingMetadataReaderFactory readerFactory=new CachingMetadataReaderFactory();
//            //MetadataReader metadataReader=readerFactory.getMetadataReader(MetaDemo.class.getName());
//            try {
//                MetadataReader metadataReader=readerFactory.getMetadataReader(new ClassPathResource("com/isg/springboottest/annotations/Metadata/MetaDemo.class"));
//                ClassMetadata classMetadata1=metadataReader.getClassMetadata();
//                AnnotationMetadata annotationMetadata1=metadataReader.getAnnotationMetadata();
//                Resource resource=metadataReader.getResource();
//                System.out.println(classMetadata1);
//                System.out.println(annotationMetadata1);
//                System.out.println(resource);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });


        //20230713 metadata end

        //20230720 eventlistener start
//        Animal dog=new Animal("dog");
//        //为事件源添加监听器
//        AnimalListener listener=new AnimalListener();
//        dog.addAnimalListener(listener);
//        //触发事件，会被事件监听器监听到
//        dog.eat();
        //20230720 eventlistener end

        //202307270956 springeventlistenerimplements start
//        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
////        //注册自定义事件监听器
//        context.addApplicationListener(new MyApplicationListener());
////        //启动上下文
//        context.refresh();
////        //发布事件，事件源为Context
//        context.publishEvent(new MyApplicationEvent(context));
//        //结束
//        context.close();
        //202307270956 springeventlistenerimplements end

        //202307270957 springeventlistenerannotation start
//           AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
//           //注册自定义事件监听器
//            context.register(MyApplicationListenerTwo.class);
//            //启动上下文
            //context.refresh();
//            //发布事件，事件源为Context
//             context.publishEvent(new MyApplicationEvent(context));
//             //结束
//             context.close();
        //202307270957 springeventlistenerannotation end



//        new SpringApplicationBuilder(Object.class)
//                .listeners(event->{System.out.println("事件对象："+event.getClass().getSimpleName()+",事件源："+event.getSource().getClass().getSimpleName());})
//                .web(WebApplicationType.NONE)
//                .run(args)
//                .close();
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
