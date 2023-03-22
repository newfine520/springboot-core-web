package com.isg.springboottest.listener;

import  com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.isg.springboottest.vo.ExcelFeildParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ExcelListener extends AnalysisEventListener<ExcelFeildParam>{
    private Map<String,Integer> map = new HashMap<>();
    private Set<String> dateSet = new HashSet<>();

    /**
     * 读取excel
     * @param excelFeildParam excel每行数据
     * @param analysisContext 上下文
     */
    @Override
    public void invoke(ExcelFeildParam excelFeildParam, AnalysisContext analysisContext) {
        calStat(excelFeildParam);
    }
    /**
     * 统计各个产品每天的舆情量
     * @param data 表格数据
     */
    public void calStat(final ExcelFeildParam data) {
        boolean flag =
                (data == null) || StringUtils.isEmpty(data.getYqDate()) || StringUtils.isEmpty(data.getCategory());
        if (flag) return;
        dateSet.add(data.getYqDate().substring(0,10));
        final String cateDate = data.getCategory().trim()+ "@@" +data.getYqDate().substring(0,10);
        final int i = (map == null || map.get(cateDate) == null) ? 1 : map.get(cateDate) + 1;
        map.put(cateDate,i);
    }

    /**
     * 读取完成调用（由于invoke对每行数据已做处理，本方法不需再进行调用）
     * @param analysisContext 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("ExcelListener  ---->文件读取完成!<---->");
    }

    public Map <String, Integer> getMap() {
        return map;
    }

    public Set<String> getDateSet() {
        return dateSet;
    }
}
