package com.wy.hadoop.five;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxMinMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	private Text keyText = new Text("Key");
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		if(line.trim().length()>0){
			context.write(keyText, new LongWritable(Long.parseLong(line.trim())));
		}
	}
}
