package com.sung.mongo.mapreduce;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TestMain {

	public ClassPathXmlApplicationContext context;

	public void befroe() {
		context = new ClassPathXmlApplicationContext(
				"spring/spring-data-mongo-config.xml");

	}

	
	public void add() {
		Data data = new Data();
		List<String> datas = new ArrayList<String>();
		datas.add("d");
		datas.add("d");
		data.setX(datas);
		MongoTemplate mongoOps = context.getBean("mongoTemplate",
				MongoTemplate.class);
		mongoOps.insert(data);
	}

	/**
	 * 计算 map reduce
	 */
	
	public void testMapReduce() {
		MongoTemplate mongoOps = context.getBean("mongoTemplate",MongoTemplate.class);
		MapReduceResults<ValueObject> results = mongoOps.mapReduce("jms1","classpath:map.js", "classpath:reduce.js",
				ValueObject.class);
		
		for (ValueObject valueObject : results) {
			System.out.println(valueObject);
		}
	}

	/**
	 * 计算 map reduce 计算结果输入到mongo数据库
	 * 
	 */
	public void testMapReduce2() {
		MongoTemplate mongoOps = context.getBean("mongoTemplate",MongoTemplate.class);
		mongoOps.mapReduce("jms1",
				"classpath:map.js", "classpath:reduce.js",
				new MapReduceOptions().outputCollection("jms1_out"),
				ValueObject.class);
	}

	public void testMapReduce3() {
		MongoTemplate mongoOps = context.getBean("mongoTemplate",MongoTemplate.class);
		Query query = new Query(Criteria.where("x").ne(new String[] { "a", "b" }));
		mongoOps.mapReduce(query,"jms1", "classpath:map.js", "classpath:reduce.js",new MapReduceOptions().options().outputCollection("jmr2_out"), ValueObject.class);
	}
}
