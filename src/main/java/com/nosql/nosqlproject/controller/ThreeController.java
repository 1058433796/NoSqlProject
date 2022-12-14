package com.nosql.nosqlproject.controller;

import com.nosql.nosqlproject.modules.repository.ObjThreeRepImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/three")
public class ThreeController {
    @Autowired
    ObjThreeRepImpl objThreeRep;

    @GetMapping("/data")
    public Object getResultData(String city, String district, int page){
        return objThreeRep.getResult(city, district, page);
    }
    @GetMapping("/districtList")
    public Object getDistrictList(String city){
        return objThreeRep.getDistrictList(city);
    }
}
