package com.isg.springboottest.entity;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class SysUser {
    private UUID id;
    private String userName;
    private String password;
    private UUID departmentId;
    private String mobile;
    private String serviceStatusCode;
    private String idCard;
    private String loginStatusCode;
    private String sexCode;
    private Date entryDate;
    private String rank;
    private String address;
    private String roleCode;
    private String DispatchTypeCode;
    private String sysItems;
    private UUID parentId;
    private String remark;
    private boolean isDeleted;
    private UUID createBy;
    private Date createTime;
    private String positionTypeCode;
    private String userNo;
    private Date leaveDate;
    private Date lastUpdateTime;
    private UUID lastUpdateBy;
    private String provinceId;
    private String cityId;
    private String districtId;
    private Date entryDateUc;
    private Date leaveDatePropose;
    private String checkNo;
    private Date birthDate;
    private String workArea;
    private String email;
    private UUID replaceId;
    private  String areaId;
    private String subAreaId;
    private  UUID positionId;
    private String registerAddress;
    private Date owsEntryDate;

}
