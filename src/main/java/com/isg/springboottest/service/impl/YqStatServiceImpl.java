package com.isg.springboottest.service.impl;

import com.alibaba.fastjson.JSON;
import com.isg.springboottest.dao.OpinionHotSpotDto;
import com.isg.springboottest.dao.ProductHotSpotDto;
import com.isg.springboottest.listener.OpinionHotSpotListener;
import com.isg.springboottest.service.UserService;
import com.isg.springboottest.service.YqStatService;
import com.isg.springboottest.vo.response.ViewResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName com.shfe.yqservice.service.YqStatService
 * @Description 舆情大屏
 * @Author lidong
 * @date 2020/8/10TAB_PRODUCT_OPINION_CNT
 **/

@Service
@Slf4j
public class YqStatServiceImpl implements YqStatService {

    @Override
    public ViewResult<Void> getStatExcel(MultipartFile file) throws ExecutionException, InterruptedException {

        Map<String,Integer> maps=new HashMap<>();
        List<String> dateList;
        Set<String> dateSet=new HashSet<>();
        List<ProductHotSpotDto> productHotSpotDtoList=new ArrayList<>();
        ArrayList<Future<OpinionHotSpotDto>> opinionHotSpotDtoList=new ArrayList<>();

        ExecutorService executorService= Executors.newFixedThreadPool(2);
        OpinionHotSpotListener opinionHotSpotListener=new OpinionHotSpotListener("excelListener",file);
        Future<OpinionHotSpotDto> excelListenerSubmit=executorService.submit(opinionHotSpotListener);
        opinionHotSpotDtoList.add(excelListenerSubmit);
        Future<OpinionHotSpotDto> calopinionListenerSubmit=executorService.submit(new OpinionHotSpotListener("calopinionlistener",file));
        opinionHotSpotDtoList.add(calopinionListenerSubmit);

        for(Future<OpinionHotSpotDto> future:opinionHotSpotDtoList)
        {
            OpinionHotSpotDto opinionHotSpotDto=future.get();
            if(Objects.nonNull(opinionHotSpotDto.getDateSet()))
            {
                dateSet = opinionHotSpotDto.getDateSet();
            }
            if(Objects.nonNull(opinionHotSpotDto.getMaps()))
            {
                maps = opinionHotSpotDto.getMaps();
            }
            if(Objects.nonNull(opinionHotSpotDto.getProdcutHotSpotDtoList()))
            {
                productHotSpotDtoList=opinionHotSpotDto.getProdcutHotSpotDtoList();
            }
        }
        return ViewResult.build(ViewResult.ViewResultStatus.SUCCESS,null);
    }
}
