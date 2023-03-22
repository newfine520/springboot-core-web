package com.isg.springboottest.util;

import java.io.File;
import java.util.UUID;

public class UploadUtils {

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认） 
    public final static String IMG_PATH_PREFIX = "upload/";

    public static File getImgDirFile(){

        File dir = new File(System.getProperty("user.dir") + File.separator + IMG_PATH_PREFIX
                +File.separator+UUID.randomUUID()+ File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }



}
