package com.nosql.nosqlproject.modules.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjFour implements Serializable {
    private String city;
    private String district;
    private String region;
    private Float average;
    private Float max;
    private Float min;
    private Integer count;
}
