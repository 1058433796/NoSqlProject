package com.nosql.nosqlproject.modules.entity.objThree;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjThree implements Serializable {
    private String city;
    private String district;
    private Integer count;
}
