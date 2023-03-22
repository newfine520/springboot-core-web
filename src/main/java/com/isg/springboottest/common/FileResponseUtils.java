package com.isg.springboottest.common;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @ClassName com.shfe.yqservice.utils.FileResponseUtil
 * @Description 文件传输发送响应流信息
 * @Author lidong
 * @dATE 2020/8/10
 **/

@Slf4j
public class FileResponseUtils {

    /**
     * 设置响应信息
     *
     * @param response 响应
     * @param filename 文件名
     */
    public static void setResponseHeader(HttpServletResponse response, String filename, Integer size) {
        try {
            filename = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info("---->UnsupportedEncodingException<---- info-->" + e.getMessage());
        }
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename*=" + filename);
//        response.setHeader("FileName", filename + ".pptx");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Content-Length", size.toString());
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }
}
