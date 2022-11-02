package com.nosql.nosqlproject.controller;

import com.nosql.nosqlproject.modules.repository.ObjOneRepImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/one")
public class OneController {
    @Autowired
    ObjOneRepImpl objOneRep;

    @GetMapping("/data")
    public Object getResultData(String city, String types, String leaseMode, int page){
        return objOneRep.getResult(city, types, leaseMode, page);
    }

    @GetMapping("/fieldList")
    public Object getFieldList(String field, String type){
        return objOneRep.getUniqueFieldList(field, type);
    }




}
