package com.isg.springboottest.vo.request;

import lombok.Data;

import java.util.List;

@Data
public class DownloadFileRequestParam {
    private List<DownloadFileParam> idList;
}
