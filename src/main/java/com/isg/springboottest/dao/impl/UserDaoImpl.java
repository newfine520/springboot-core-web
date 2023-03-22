package com.isg.springboottest.dao.impl;

import com.isg.springboottest.annotations.MyAnn;
import com.isg.springboottest.pojo.User;
import com.isg.springboottest.dao.UserDao;
import com.isg.springboottest.vo.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @MyAnn
    @Override
    public User get(Integer id)
    {
        User user;
        try {
            user = (User) jdbcTemplate.queryForObject("select * from User where uid=?", new Object[]{id}, (rs, rownum) -> {
                User temp = new User();
//                temp.setId(rs.getLong("uid"));
                temp.setRealName(rs.getString("RealName"));
                temp.setInternalNumber(rs.getInt("InternalNumber"));
                return temp;
            });
        }
        catch (Exception e)
        {
            return  null;
        }
        return  user;
    }
    @MyAnn
    @Override
    public  Integer deleteById(Integer id)
    {
      int i=jdbcTemplate.update("delete from User where uid=?",id);
      return  i;
    }
    @MyAnn
    @Override
    public  Integer update(User user)
    {
        int i=0;
      //int i=jdbcTemplate.update("update tms_user set realname=?,InternalNumber=? where uid=?",user.getName(),user.getAge(),user.getId());
      return  i;
    }
    @MyAnn
    @Override
    public Integer insert(UserRequest user) throws  Exception
    {
        try
        {
//        for(int i=0;i<2;i++)
//        {
//            if(i==1)
//            {
//                //throw new FileNotFoundException("非运行时异常");
//                //throw new NullPointerException("运行时异常");
//            }
            //jdbcTemplate.execute("insert into tms_user(realname,email,InternalNumber,lastloginTime) values(\"" + user.getRealName() + "\",\""+user.getEmail()+"\",\""+user.getInternalNumber()+"\",\""+user.getLastLoginTime()+"\")");
            jdbcTemplate.execute("insert into tms_user(realname,email,InternalNumber) values(\"" + user.getRealName() + "\",\""+user.getEmail()+"\",\""+user.getInternalNumber()+"\")");
            //}
            return 1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }

    }
}
