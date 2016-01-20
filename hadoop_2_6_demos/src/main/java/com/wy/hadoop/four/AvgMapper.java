package com.wy.hadoop.four;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AvgMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		if(line.trim().length()>0){
			String[] arr = line.split("\t");
			if(arr.length==2){
				context.write(new Text(arr[0]), new IntWritable(Integer.valueOf(arr[1])));
			}
		}
	}
}
