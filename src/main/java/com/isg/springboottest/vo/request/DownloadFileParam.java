package com.isg.springboottest.vo.request;

import lombok.Data;

import java.util.List;

@Data
public class DownloadFileParam {
    private String id;
    private Integer dirFlag;
}
