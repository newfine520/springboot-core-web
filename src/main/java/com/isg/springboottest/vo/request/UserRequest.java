package com.isg.springboottest.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isg.springboottest.annotations.MyValid;
import com.isg.springboottest.util.BirthdayValidHandler;
import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.logging.log4j.message.Message;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@MyValid(message = "年龄和生日不匹配", handler = BirthdayValidHandler.class)
public class UserRequest {
    @NotNull(message = "名字不能为空")
    private String realName;
    @Min(value = 10,message ="InternalNumber不能小于10" )
    @Max(value=100,message ="InternalNumber不能大于100")
    private Integer internalNumber;
    @Email(message = "邮箱格式不正确")
    private String email;
    //@Past(message = "不能大于当前时间")
    private Date lastLoginTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer age;
    private Integer uid;
    //20211117注意变量名字规范很重要，取这样的名字：uIDS、uIds，controller接收不到值，找了好久的问题。。
    private List<Integer> userIds;
    private Integer page;//当前页
    private Integer limit;//每页步长
    private Long count;//总条数
}
