package com.tiansu.energy.ltc.module.demo.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Student {

    @ExcelProperty(value = "姓名",index = 0)
    private String name;
    @ExcelProperty(value = "身份证",index = 1)
    private String idCard;
    @ExcelProperty(value = "年龄",index = 2)
    private int age;
    @ExcelProperty(value = "性别",index = 3)
    private String gender;

    private String address;

}
