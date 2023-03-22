package com.isg.springboottest.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

/**
 * @ClassName: com.shfe.yqservice.viewparam.CalcOpinionParam
 * @Descroption: 舆情报告参数
 * @Atuhor: lidong
 * @Date: 2021/9/16
 **/
@Data
public class CalcOpinionParam {

    /** 账户组编号 */
    @ExcelProperty(index = 1)
    private String category;

    /** 账户组编号 */
    @ExcelProperty(index = 2)
    private String headline;

    /** 账户组编号 */
    @ExcelProperty(index = 4)
    @DateTimeFormat("yyyy-MM-dd")
    private String publishdate;
}
