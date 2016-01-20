package com.wy.hadoop.eighteen;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper2 extends Mapper<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void map(Text key, IntWritable value,Context context)
			throws IOException, InterruptedException {
		if(value.get()<100){
			context.write(key, value);
		}
	}

	
}
