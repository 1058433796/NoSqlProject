package com.nosql.nosqlproject.modules.entity.objOne;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjOne implements Serializable {
    private String city;
    private String types;
    private String leaseMode;
    private Float average;
    private Float medium;
    private Float max;
    private Float min;
//    符合条件的数量 用于中位数查找
    private Integer count;
//    暂时储存中位数价格
    private Float price;

}
