package com.isg.springboottest.common;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

public class FileUploadUtils {
    public static void download(HttpServletRequest request, HttpServletResponse response, InputStream fis, String fileName) {
    try {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream sos=response.getOutputStream();
        byte[] buffer=new byte[1024*8];
        int length=0;
        while ((length=fis.read(buffer))!=-1)
        {
            sos.write(buffer,0,length);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
