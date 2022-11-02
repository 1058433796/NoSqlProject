package com.nosql.nosqlproject.modules.repository;

import com.nosql.nosqlproject.modules.entity.objThree.ObjThree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class ObjThreeRepImpl implements Repository {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<ObjThree> getResult(String city, int page){
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        if(city != null) operations.add(Aggregation.match(Criteria.where("city").is(city)));
        operations.add(Aggregation.group("city", "district")
                .first("city").as("city")
                .first("district").as("district")
                .count().as("count"));
        operations.add(Aggregation.sort(Sort.Direction.DESC, "count"));
        operations.add(Aggregation.skip(15 * (page - 1)));
        operations.add(Aggregation.limit(15));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        System.out.println(aggregation);
        AggregationResults<ObjThree> results = mongoTemplate.aggregate(aggregation, "house", ObjThree.class);
        return results.getMappedResults();
    }
}
