package com.isg.springboottest.service;

import com.isg.springboottest.vo.request.DownloadFileRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    void batchDownloadFiles(DownloadFileRequestParam params, String userId, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
