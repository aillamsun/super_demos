package com.wy.hadoop.five;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxMinReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
	
	@Override
	protected void reduce(Text key, Iterable<LongWritable> values,Context context)
			throws IOException, InterruptedException {
		long max = Long.MIN_VALUE;
		long min = Long.MAX_VALUE;
		for(LongWritable val:values){// 10,20,8
			if(val.get()>max){
				max=val.get();//20
			}
			if(val.get()<min){
				min = val.get();//8
			}
		}
		context.write(new Text("Max"), new LongWritable(max));
		context.write(new Text("Min"), new LongWritable(min));
		
	}
}
