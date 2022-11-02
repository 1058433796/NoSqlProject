package com.nosql.nosqlproject.controller;

import com.nosql.nosqlproject.modules.repository.ObjTwoRepImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/two")
public class TwoController {
    @Autowired
    ObjTwoRepImpl objTwoRep;

    @RequestMapping("/data")
    public Object getResultData(String city, Integer month, int page){
        return objTwoRep.getResult(city, month, page);
    }
}
