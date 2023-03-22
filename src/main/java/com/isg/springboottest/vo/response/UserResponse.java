package com.isg.springboottest.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class UserResponse {
    private Integer uID;
    private String realName;
    private String mobile;
    private String email;
    private Integer internalNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
