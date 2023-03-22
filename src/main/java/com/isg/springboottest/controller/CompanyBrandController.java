package com.isg.springboottest.controller;

import com.isg.springboottest.service.ICompanyBrandService;
import com.isg.springboottest.vo.request.CombrandRequest;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.MyResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CompanyBrandController {
    @Autowired
    private ICompanyBrandService companyBrandService;
    @RequestMapping("/show")
    public String showInfo(Model model){
        model.addAttribute("msg","Thymeleaf Hello ....122211");
        return "index";
    }
    @ApiOperation(value = "添加品牌信息")
    @PostMapping("/companyBrand/addBrand")
    @ResponseBody
    public MyResponseEntity create(@RequestBody CombrandRequest combrandRequest) {
        //20211119 接口数据有效性验证 todo
        //ValidatorUtil.validateEntity(vo, AddGroup.class);
        return companyBrandService.insertOrUpdateCompanyBrand(combrandRequest);
    }
    @ApiOperation(value = "修改品牌信息")
    @PostMapping("/companyBrand/updateBrand")
    public MyResponseEntity modify(@RequestBody CombrandRequest combrandRequest) {
        //20211119 接口数据有效性验证 todo
        //ValidatorUtil.validateEntity(vo, AddGroup.class);
        return companyBrandService.insertOrUpdateCompanyBrand(combrandRequest);
    }
}
