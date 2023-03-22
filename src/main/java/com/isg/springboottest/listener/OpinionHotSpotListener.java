package com.isg.springboottest.listener;

import com.alibaba.excel.EasyExcelFactory;
import com.isg.springboottest.dao.OpinionHotSpotDto;
import com.isg.springboottest.dao.ProductHotSpotDto;
import com.isg.springboottest.vo.ExcelFeildParam;
import com.isg.springboottest.vo.ProductHotSpotParam;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.Callable;
@Slf4j
@Data
public class OpinionHotSpotListener implements Callable<OpinionHotSpotDto> {
    ExcelListener excelListener;
    CalcOpinionListener calcOpinionListener;
    MultipartFile multipartFile;
    private Map<String, Integer> maps=new HashMap<>();
    private Set<String> dateSet=new HashSet<>();
    public OpinionHotSpotListener(String listenName,MultipartFile file)
    {
        if(listenName.equals("excelListener"))
        {
            excelListener=new ExcelListener();
        }
        else if(listenName.equals("calopinionlistener"))
        {
            calcOpinionListener=new CalcOpinionListener();
        }
        multipartFile=file;
    }
    @Override
    public OpinionHotSpotDto call() throws Exception
    {
        OpinionHotSpotDto opinionHotSpotDto=new OpinionHotSpotDto();
        if(excelListener!=null)
        {
            ExcelListener excelListener=new ExcelListener();
            EasyExcelFactory.read(multipartFile.getInputStream(), ExcelFeildParam.class,excelListener);
            maps=excelListener.getMap();
            opinionHotSpotDto.setMaps(maps);
            dateSet=excelListener.getDateSet();
            opinionHotSpotDto.setDateSet(dateSet);
        }
        else if(calcOpinionListener!=null)
        {
            List<ProductHotSpotDto> productHotSpotDtoList=new ArrayList<>();
            ProductHotSpotListener productHotSpotListener=new ProductHotSpotListener();
            EasyExcelFactory.read(multipartFile.getInputStream(), ProductHotSpotParam.class,productHotSpotListener);
            productHotSpotDtoList=productHotSpotListener.getList();
            opinionHotSpotDto.setProdcutHotSpotDtoList(productHotSpotDtoList);
            dateSet=productHotSpotListener.getDateSet();
            opinionHotSpotDto.setDateSet(dateSet);
        }
        return opinionHotSpotDto;
    }
}
