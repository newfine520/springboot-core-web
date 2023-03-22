package com.isg.springboottest.service;

import com.isg.springboottest.vo.response.ViewResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

public interface YqStatService {
    ViewResult<Void> getStatExcel(MultipartFile file) throws ExecutionException, InterruptedException;
}
