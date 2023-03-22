package com.isg.springboottest.entity;

import java.util.List;

public class Result {
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Animal> getValues() {
        return values;
    }

    public void setValues(List<Animal> values) {
        this.values = values;
    }

    public Integer year;
    public List<Animal> values;
    public String toString()
    {
        int index=0;
        String result;
        result="{\"year\":\""+year+"\",\"values\":[";
        for(Animal animal:values)
        {
            if(index==values.size()-1)
            {
                result += "{\"month\":" + animal.getMonth() + ",\"value\":" + animal.getValue() + "}";
            }
            else {
                result += "{\"month\":" + animal.getMonth() + ",\"value\":" + animal.getValue() + "},";
            }
            index++;
        }
        result+="]}";
        return result;
    }

}
