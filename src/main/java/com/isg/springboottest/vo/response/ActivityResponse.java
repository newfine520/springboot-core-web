package com.isg.springboottest.vo.response;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("Activity")
@Data
public class ActivityResponse {
    private Integer id;
    private String activityName;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private LocalDateTime starttime;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private LocalDateTime endtime;
}
