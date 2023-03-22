package com.isg.springboottest.vo.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain =true)
public class FileUploadResponse {
    private String url;


    private List<String> urls;
}
