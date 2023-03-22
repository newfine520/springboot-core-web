package com.isg.springboottest.entity;

import java.util.List;

public class ResponseResult {
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<ResponseAnimal> getValues() {
        return values;
    }

    public void setValues(List<ResponseAnimal> values) {
        this.values = values;
    }

    public Integer year;
    public List<ResponseAnimal> values;
}
