package com.isg.springboottest.util;

import com.isg.springboottest.annotations.MyValid;
import com.isg.springboottest.annotations.MyValidHandler;
import com.isg.springboottest.vo.request.UserRequest;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class BirthdayValidHandler implements MyValidHandler<UserRequest> {
    @Override
    public boolean valid(MyValid myValid, UserRequest data) {
        Integer age = data.getAge();
        Date birthday = data.getBirthday();
        if (age == null || birthday == null) return true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currYear = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int birYear = calendar.get(Calendar.YEAR);
        return currYear - birYear == age;
    }
}
