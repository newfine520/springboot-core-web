package com.isg.springboottest.dao;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class ProductHotSpotDto {
    private Set<String> dateSet;
    private List<ProductHotSpotDto> prodcutHotSpotDtoList;

}
