package com.nosql.nosqlproject.modules.repository;

import com.nosql.nosqlproject.modules.entity.objTwo.ObjTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjTwoRepImpl implements Repository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<ObjTwo> getResult(String city, Integer month, int page){
        ArrayList<AggregationOperation> operations = new ArrayList<>();
//        添加city过滤
        if(city != null) operations.add(Aggregation.match(Criteria.where("city").is(city)));
//        添加month过滤
        if(month != null) operations.add(Aggregation.match(Criteria.where("month").is(month)));
//        聚合操作
        operations.add(Aggregation.group("city", "month:$base_info.month")
                .first("city").as("city")
                .first("$base_info.month").as("month")
                .count().as("count"));
//        分页操作
        operations.add(Aggregation.skip(15 * (page - 1)));
        operations.add(Aggregation.limit(15));
        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults<ObjTwo> results =  mongoTemplate.aggregate(aggregation, "house", ObjTwo.class);
        return results.getMappedResults();
    }
}
