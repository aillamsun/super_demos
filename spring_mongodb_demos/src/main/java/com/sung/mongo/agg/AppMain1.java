package com.sung.mongo.agg;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

//import org.junit.Before;
//import org.junit.Test;

public class AppMain1 {
	
	public static ClassPathXmlApplicationContext context;
	static MongoTemplate mongoOps ;
	//@Before
	public static void befroe() {
		context = new ClassPathXmlApplicationContext("spring/spring-data-mongo-config.xml");
		mongoOps = context.getBean("mongoTemplate",MongoTemplate.class);
	}
	
	//@Test
	public static void getHostingCount(){
		Aggregation agg = newAggregation(
				//
				//match(Criteria.where("_id").lt(10))
//				match(Criteria.where("age").lt(30)),
//				match(Criteria.where("score").lt(80)),
//				match(Criteria.where("name").is("aa"))
				match(Criteria.where("domainName").is("test1.com")),
				//project("_id","hosting"),
				//sort(Sort.Direction.ASC,"hosting")
				//设置分组字段  增加COUNT为分组后输出的字段  增加_id为分组后输出的字段
				group("hosting").count().as("total"),
				
				//重新挑选字段 ,为前一操作所产生的ID FIELD建立别名
				project("total").and("hosting").previousOperation()
				//sort(Sort.Direction.DESC,"total")
		);
		AggregationResults<HostingCount> groupResults = mongoOps.aggregate(agg, "domain", HostingCount.class);
		List<HostingCount> result = groupResults.getMappedResults();
		for (HostingCount hostingCount : result) {
			System.out.println(hostingCount);
		}

	}


	public static void getHostingCount2(){
		Aggregation agg = newAggregation(
				match(Criteria.where("age").is(30)),
				match(Criteria.where("age").is(21)),
				match(Criteria.where("age").is(24))
		);
		AggregationResults<HostingCount> groupResults = mongoOps.aggregate(agg, "users", HostingCount.class);
		List<HostingCount> result = groupResults.getMappedResults();
		for (HostingCount hostingCount : result) {
			System.out.println(hostingCount);
		}
	}

	public static void main(String[] args) {
		befroe();;
		getHostingCount2();
	}
}
