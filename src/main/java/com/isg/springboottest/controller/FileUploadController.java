package com.isg.springboottest.controller;

import com.isg.springboottest.annotations.PackResponse;
import com.isg.springboottest.util.OSSUtils;
import com.isg.springboottest.util.UploadUtils;
import com.isg.springboottest.vo.response.FileUploadResponse;
import org.aspectj.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class FileUploadController {
    @PackResponse
    @PostMapping("/imgs/upload")
    public  Object upload(MultipartFile file) throws Exception {
        File newFile=new File(UploadUtils.getImgDirFile(),file.getOriginalFilename());
        try
        {
            file.transferTo(newFile);
            return new FileUploadResponse().setUrl(OSSUtils.uploadFile(newFile));
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally {
            //FileUtil.del(newFile.getParentFile());
        }
    }
}
