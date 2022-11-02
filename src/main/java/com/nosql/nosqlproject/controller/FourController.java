package com.nosql.nosqlproject.controller;

import com.nosql.nosqlproject.modules.repository.ObjFourRepImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/four")
public class FourController {
    @Autowired
    ObjFourRepImpl objFourRep;

    @GetMapping("/data")
    public Object getResultData(String city, String district, int page){
        return objFourRep.getResult(city, district, page);
    }

}
