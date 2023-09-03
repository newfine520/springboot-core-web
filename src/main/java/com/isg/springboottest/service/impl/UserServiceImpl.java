package com.isg.springboottest.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.isg.springboottest.dao.UserDao;
import com.isg.springboottest.dao.impl.UserDaoImpl;
import com.isg.springboottest.mapper.UserMapper;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.service.UserService;
import com.isg.springboottest.vo.request.UserRequest;
import com.isg.springboottest.vo.response.UserResponse;
import com.isg.springboottest.vo.response.UserResponseForPage;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.processor.SpringUErrorsTagProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserDao userDao;
    @Autowired
    protected UserMapper userMapper;
    @Override
    public  User get(Integer id)
    {
        return userDao.get(id);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = ArithmeticException.class )
    @Override
    public  Integer deleteById(Integer id) throws Exception
    {
        User updateUser=new User();
        updateUser.setUID(id);
        updateUser.setRealName("凯文·杜兰特");
        int i=userMapper.updateById(updateUser);

        return  i;
    }
    @Override
    public Integer insert(User user)
    {
        return userMapper.insert(user);
    }
    @Override
    public Integer insert(UserRequest userRequest)
    {
        int i=userDao.insert(userRequest);
        return  i;
    }
    @Override
    public  Integer update(User user)
    {
        int i=userDao.update(user);
        return  i;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer insertWithTransaction(User user) throws Exception
    {
        try
        {
            userMapper.insert(user);
        }
        catch (Exception e)
        {
            throw  e;
        }
        return 0;
    }
    @Override
    //@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class},isolation = Isolation.REPEATABLE_READ)
    public  Integer insertWithRollBack(User user) throws  Exception
    {
        Integer result=insertWithTransaction(user);
//        try {
//            User updateUser = new User();
//            updateUser.setUID(2);
//            updateUser.setRealName("凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特");
//            int i = userMapper.updateById(updateUser);
//        }
//        catch (Exception e)//捕获并抛出异常，事务生效，否则事务不生效
//        {
//            throw e;
//        }
        User updateUser = new User();
        updateUser.setUID(28);
        updateUser.setRealName("凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特凯文·杜兰特");
        insertWithTransaction(updateUser);
        return 0;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED,noRollbackFor = {IllegalArgumentException.class},isolation = Isolation.REPEATABLE_READ)
    public Integer insertWithoutRollBack(User user)
    {
        Integer result=userMapper.insert(user);
        if(user.getRealName().equals("张三"))
        {
            throw new IllegalArgumentException("张三已存在，数据将不会回滚");
        }
        return result;
    }
    @Override
    public List<UserResponse> listUser(UserRequest userRequest)
    {

        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.like("RealName",userRequest.getRealName());
        List<User> activities=userMapper.selectList(wrapper);
        //List<User> activities = userMapper.listUser(userRequest);
        if (CollectionUtils.isEmpty(activities)) {
            return Lists.newArrayList();
        }
        List<UserResponse> list = activities.stream().map(a -> {
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(a, response);
            return response;
        }).collect(Collectors.toList());
        return list;
    }
    @Override
    public UserResponseForPage getListForPage(UserRequest userRequest)
    {
        List<User> list=new ArrayList<>();
        if(userRequest.getPage()!=null&&userRequest.getLimit()!=null)
        {
            PageHelper.startPage(userRequest.getPage(),userRequest.getLimit());
            list=userMapper.getListForPage(userRequest);
            PageInfo<User> pageInfo=new PageInfo<>(list);
            userRequest.setCount(pageInfo.getTotal());
        }
        UserResponseForPage userResponseForPage=new UserResponseForPage();
        List<UserResponse> list1 = list.stream().map(a -> {
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(a, response);
            return response;
        }).collect(Collectors.toList());
          userResponseForPage.responses=list1;
          userResponseForPage.setCount(userRequest.getCount());
          userResponseForPage.setPage(userRequest.getPage());
          userResponseForPage.setLimit(userRequest.getLimit());
          return userResponseForPage;
    }
}
