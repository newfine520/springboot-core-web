package com.isg.springboottest.vo.request;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;
@Data
public class CombrandRequest {
    private Integer id;
    private String name;
    private String code;
    private String helpId;
    private String smallPhoto;
    private String logoPhoto;
    private String themePhoto;
    private String msgUrl;
    private String manageUrl;
    private String isgCode;
    private String wise;
    private String appKey;
    private String secret;
}
