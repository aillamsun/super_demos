package com.wy.hadoop.eighteen;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper3 extends Mapper<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void map(Text key, IntWritable value,Context context)
			throws IOException, InterruptedException {
		if(key.toString().length()<3){
			context.write(key, value);
		}
	}

	
}
