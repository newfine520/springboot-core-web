package com.isg.springboottest.listener;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.isg.springboottest.constant.CategoryEnum;
import com.isg.springboottest.dao.OpinionHotSpotDto;
import com.isg.springboottest.dao.ProductHotSpotDto;
import com.isg.springboottest.vo.CalcOpinionParam;
import com.isg.springboottest.vo.ExcelFeildParam;
import com.isg.springboottest.vo.ProductHotSpotParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.Callable;
@Slf4j
@Data
public class ProductHotSpotListener extends AnalysisEventListener<ProductHotSpotParam> {
    private List<String> codeList;
    private List<CalcOpinionParam> dataList=new ArrayList<>();
    private List<ProductHotSpotDto> list;
    private Set<String> dateSet;
    @Override
    public void invoke(ProductHotSpotParam data, AnalysisContext analysisContext)
    {
        Optional.ofNullable(data).filter(e-> StringUtils.isNotBlank(e.getCategory()))
                .filter(u->StringUtils.isNotBlank(u.getPublishdate()))
                .filter((u->StringUtils.isNotBlank(u.getHeadline())))
                .ifPresent(u->{
                    u.setCategory(CategoryEnum.getCatecode(u.getCategory()));
//                    if(CollectionUtils.isEmpty(codeList))
//                    {
//                        dataList.add(u);
//                        return;
//                    }
//                    if(codeList.contains(u.getCategory()))
//                    {
//                        dataList.add(u);
//                    }
                });
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("ExcelListener  ---->文件读取完成!<---->");
    }
//    public ProductHotSpotListener(String listenName, MultipartFile file)
//    {
//        if(listenName.equals("excelListener"))
//        {
//            excelListener=new ExcelListener();
//        }
//        else if(listenName.equals("calopinionlistener"))
//        {
//            calcOpinionListener=new CalcOpinionListener();
//        }
//        multipartFile=file;
//    }
//    @Override
//    public ProductHotSpotDto call() throws Exception
//    {
//        OpinionHotSpotDto opinionHotSpotDto=new OpinionHotSpotDto();
//        if(excelListener!=null)
//        {
//            ExcelListener excelListener=new ExcelListener();
//            EasyExcelFactory.read(multipartFile.getInputStream(), ExcelFeildParam.class,excelListener);
//            maps=excelListener.getMap();
//            opinionHotSpotDto.setMaps(maps);
//            dateSet=excelListener.getDateSet();
//            opinionHotSpotDto.setDateSet(dateSet);
//        }
//        else if(calcOpinionListener!=null)
//        {
//            List<ProductHotSpotDto> productHotSpotDtoList=new ArrayList<>();
//            EasyExcelFactory.read(multipartFile.getInputStream(), ProductHotSpotParam.class,excelListener);
//            maps=excelListener.getMap();
//            opinionHotSpotDto.setMaps(maps);
//            dateSet=excelListener.getDateSet();
//            opinionHotSpotDto.setDateSet(dateSet);
//        }
//    }

}
