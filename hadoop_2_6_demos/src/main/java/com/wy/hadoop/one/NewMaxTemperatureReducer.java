package com.wy.hadoop.one;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NewMaxTemperatureReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context)
			throws IOException, InterruptedException {
		int max_value = 0;
		for(IntWritable val: values){
			max_value = Math.max(max_value, val.get());
		}
		context.write(key, new IntWritable(max_value));
	}

}
