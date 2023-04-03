package com.isg.springboottest.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;

@Slf4j
@Component
public class OSSUtils implements InitializingBean {

    /**
     * 图片路径
     */
    private final static String IMG_PATH = "sfaworkbench";
    /**
     * oss ENDPOINT
     */
    private static String ENDPOINT;//  = "oss-cn-shanxfghai.aliyuncs.com";
    /**
     * oss ACCESS_KEY_ID
     */
    private static String ACCESS_KEY_ID; // = "LTAI4GL333kCX9cML5Ypim2Q";
    /**
     * oss ACCESS_KEY_SECRET
     */
    private static String ACCESS_KEY_SECRET; //= "ev394M8bVojZI3UyyJqfAbR6bwY6w6";
    /**
     * oss BUCKET_NAME
     */
    private static String BUCKET_NAME; // = "newisg-app-plus";
    private static OSS OSS_CLIENT;
    //当配置项无法解析时，取默认值
    @Value("${oss.resource.endpoint:oss-cn-shanghai.aliyuncs.com}")
    public String endpoint;
    @Value("${oss.resource.accessKeyId:abcdefghijklmnopqrstuvwxyz}")
    public String accessKeyId;
    @Value("${oss.resource.accessKeySecret:abcdefghijklmnopqrstuvwxyz}")
    private String accessKeySecret;
    @Value("${oss.resource.bucketName:sfa-workbench-plus}")
    private String bucketName;



    public static String uploadFile(File f) {

        String uploadPath = IMG_PATH  + "/" +f.getParentFile().getName()+"/"+ f.getName();

        OSS_CLIENT = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 上传至oss

        OSS_CLIENT.putObject(BUCKET_NAME,uploadPath,f );

        // 生成oss 访问的url
        String imgUrl = "https://sfa-" +
                "plus.oss-cn-shanghai.aliyuncs.com/" + uploadPath;

        return imgUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ACCESS_KEY_ID = accessKeyId;
        BUCKET_NAME = bucketName;
        ACCESS_KEY_SECRET = accessKeySecret;
    }
    public static OSSObject downLoad(String url) {
        Assert.hasLength(url, "文件名不能为null");
        String name = url.substring("http://sfa-workbench-plus.oss-cn-shanghai.aliyuncs.com/".length());
        OSS_CLIENT = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return OSS_CLIENT.getObject(BUCKET_NAME, name);
    }


}
