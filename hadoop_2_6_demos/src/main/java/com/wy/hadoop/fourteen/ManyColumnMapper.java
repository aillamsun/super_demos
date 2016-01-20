package com.wy.hadoop.fourteen;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ManyColumnMapper extends Mapper<Object, Text, IntPair, IntWritable> {
	
	@Override
	protected void map(Object key, Text value,Context context)
			throws IOException, InterruptedException {
		String first = key.toString().trim();
		Integer second = Integer.parseInt(value.toString().trim());
		context.write(new IntPair(first,second), new IntWritable(second));
	}
}
