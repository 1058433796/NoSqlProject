package com.nosql.nosqlproject.modules.repository;

import com.nosql.nosqlproject.modules.entity.objOne.ObjOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationPipeline;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ObjOneRepImpl implements Repository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ObjOne> getResult(String city, String types, String leaseMode, int page){
        ArrayList<AggregationOperation> operations = new ArrayList<>();
//        过滤城市
        if(city != null)operations.add(Aggregation.match(Criteria.where("city").is(city)));
//        过滤类型
        if(types != null)operations.add(Aggregation.match(Criteria.where("types").is(types)));
//        过滤租赁方式
        if(leaseMode != null) operations.add(Aggregation.match(Criteria.where("lease_mode").is(leaseMode)));
//        聚合操作
        operations.add(Aggregation.group("city: $city", "types: $base_info.types", "leaseMode: $lease_mode")
                .first("city").as("city")
                .first("$base_info.types").as("types")
                .first("lease_mode").as("leaseMode")
                .avg("price").as("average")
                .min("price").as("min")
                .max("price").as("max")
                .count().as("count"));

        operations.add(Aggregation.skip(15 * (page - 1)));
//        查询数量限制
        operations.add(Aggregation.limit(15));
//        生成聚合查询
        Aggregation aggregation =  Aggregation.newAggregation(operations);

        AggregationResults<ObjOne> results = mongoTemplate.aggregate(aggregation, "house", ObjOne.class);
        List<ObjOne> resultList = results.getMappedResults();

//        计算中位数
        for(ObjOne objOne: resultList){
            Query query = new Query(Criteria.where("city").is(objOne.getCity())
                    .and("lease_mode").is(objOne.getLeaseMode())
                    .and("base_info.types").is(objOne.getTypes()))
                    .with(Sort.by(Sort.Direction.ASC, "price"))
                    .skip(objOne.getCount() / 2 - 1).limit(1);
            query.fields().include("price");
            Float price = mongoTemplate.findOne(query, ObjOne.class, "house").getPrice();
            objOne.setMedium(price);
        }
        return resultList;
    }

    public List getUniqueFieldList(String field, String type) {
        Class cls = null;
        if(type.equals("Integer"))cls = Integer.class;
        else if(type.equals("String")) cls = String.class;
        return mongoTemplate.findDistinct(new Query(), field, "house", cls);
    }
}
