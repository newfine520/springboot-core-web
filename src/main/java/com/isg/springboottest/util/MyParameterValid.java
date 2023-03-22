package com.isg.springboottest.util;

import com.isg.springboottest.annotations.MyValid;
import com.isg.springboottest.annotations.MyValidHandler;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Slf4j
public class MyParameterValid implements ConstraintValidator<MyValid, Object> {
    private MyValid myValid;
    @Override
    public void initialize(MyValid constraintAnnotation) {
        this.myValid = constraintAnnotation;
    }
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        log.info("自定义参数校验触发" + o);
        if (null != o) {
            Class<? extends MyValidHandler> handler = myValid.handler();
            // 交给 MyValidHandler 处理校验
            MyValidHandler myValidHandler = ApplicationContextUtils.getBean(handler);
            return Optional
                    .ofNullable(myValidHandler)
                    .map(myValidHandler1 -> {
                        return myValidHandler.valid(myValid, o);
                    }).orElse(false);
        }
        return true;
    }
}
