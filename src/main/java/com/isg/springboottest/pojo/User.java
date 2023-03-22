package com.isg.springboottest.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@TableName("User")
@Data
public class User {
   @TableId(value = "UID")
    private Integer UID;
    @TableField(value = "RealName")
    private String RealName;
    @TableField(value = "Mobile")
    private String Mobile;
    @TableField(value = "Email")
    private String Email;
    @TableField(value = "InternalNumber")
    private Integer InternalNumber;
    @TableField(value = "CreateTime")
    private LocalDateTime CreateTime;
    @TableField(value = "IsDelete")
    private Integer IsDelete;
    @TableField(value = "LinkAddress")
    private String LinkAddress;
}
