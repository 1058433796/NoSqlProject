package com.nosql.nosqlproject.modules.repository;

import com.nosql.nosqlproject.modules.entity.objThree.ObjThree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjThreeRepImpl implements Repository {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<ObjThree> getResult(String city, String district, int page){
        ArrayList<AggregationOperation> operations = new ArrayList<>();
//        对城市筛选
        if(city != null) operations.add(Aggregation.match(Criteria.where("city").is(city)));
//        对区筛选
        if(district != null) operations.add(Aggregation.match(Criteria.where("district").is(district)));
//        聚合操作
        operations.add(Aggregation.group("city", "district", "region")
                .first("city").as("city")
                .first("district").as("district")
                .first("region").as("region")
                .count().as("count"));
//        按照数量降序排序
        operations.add(Aggregation.sort(Sort.Direction.DESC, "count"));
        operations.add(Aggregation.skip(15 * (page - 1)));
//        取前15个作为热点区域
        operations.add(Aggregation.limit(15));

        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults<ObjThree> results = mongoTemplate.aggregate(aggregation, "house", ObjThree.class);
        return results.getMappedResults();
    }

    public List<String> getDistrictList(String city){
        Query query = new Query(Criteria.where("city").is(city));
        query.fields().include("district");
        return mongoTemplate.findDistinct(query, "district", "house", String.class);
    }
}
