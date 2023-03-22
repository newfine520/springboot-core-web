package com.isg.springboottest.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@TableName("Activity")
@Data
public class Activity {
    @TableId(value = "id")
    private Integer id;
    @TableField(value = "activity_no")
    private String activityNo;
    @TableField(value = "activity_name")
    private String activityName;
    @TableField(value = "starttime")
    private LocalDateTime starttime;
    @TableField(value = "endtime")
    private LocalDateTime endtime;
    @TableField(value = "create_by")
    private String createBy;
    @TableField(value = "create_by_name")
    private String createByName;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "last_update_by")
    private String lastUpdateBy;
    @TableField(value = "last_update_by_name")
    private String lastUpdateByName;
    @TableField(value = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @TableField(value = "is_deleted")
    private Integer isDeleted;
    @TableField(value = "store_id")
    private String storeId;
}