package com.sung.mongo.mapreduce;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="jms1")
public class Data {
		
	private List<String> x;

	public List<String> getX() {
		return x;
	}

	public void setX(List<String> x) {
		this.x = x;
	}

}
