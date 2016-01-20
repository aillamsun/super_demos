package com.wy.hadoop.six;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class TopNReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

	int len ;
	int[] top;
	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> values,Context context)
			throws IOException, InterruptedException {
		add(key.get());
	}
	
	private void add(int payment){
		top[0] = payment;
		Arrays.sort(top);
	}
	
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		len = context.getConfiguration().getInt("N", 10);
		top = new int[len+1];
	}
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		for(int i = len;i>0;i--){
			context.write(new IntWritable(len-i+1), new IntWritable(top[i]));
		}
	}
}
