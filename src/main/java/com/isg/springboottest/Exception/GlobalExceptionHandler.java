package com.isg.springboottest.Exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    String handlerException(SQLException e)
    {
        return "SQL异常！";
    }
}
