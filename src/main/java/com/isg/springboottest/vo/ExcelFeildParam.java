package com.isg.springboottest.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

/**
 * @ClassName com.shfe.yqservice.viewparam.ExcelFeildParam
 * @Description excel表格中的字段
 * @Author lidong
 * @Date 2020/8/20
 **/

@Data
public class ExcelFeildParam {

    /**
     * 产品名称
     */
    @ExcelProperty("Category")
    private String category;

    /**
     * 舆情日期
     */
    @ExcelProperty(value = "Date(Time)")
    @DateTimeFormat("yyyy-MM-dd")
    private String yqDate;
}