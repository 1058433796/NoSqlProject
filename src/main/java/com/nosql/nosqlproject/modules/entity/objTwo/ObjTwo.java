package com.nosql.nosqlproject.modules.entity.objTwo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjTwo implements Serializable {
    private String city;
    private Integer month;
    private Integer count;
}
