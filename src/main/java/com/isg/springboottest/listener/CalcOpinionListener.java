package com.isg.springboottest.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.isg.springboottest.constant.CategoryEnum;
import com.isg.springboottest.vo.CalcOpinionParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
public class CalcOpinionListener extends AnalysisEventListener<CalcOpinionParam> {
    private List<String> codeList;
    private List<CalcOpinionParam> dataList=new ArrayList<>();

    @Override
    public void invoke(CalcOpinionParam data, AnalysisContext analysisContext)
    {
        Optional.ofNullable(data).filter(e-> StringUtils.isNotBlank(e.getCategory()))
                .filter(u->StringUtils.isNotBlank(u.getPublishdate()))
                .filter((u->StringUtils.isNotBlank(u.getHeadline())))
                .ifPresent(u->{
                    u.setCategory(CategoryEnum.getCatecode(u.getCategory()));
                    if(CollectionUtils.isEmpty(codeList))
                    {
                        dataList.add(u);
                        return;
                    }
                    if(codeList.contains(u.getCategory()))
                    {
                        dataList.add(u);
                    }
                });
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("ExcelListener  ---->文件读取完成!<---->");
    }
}
