package com.wy.hadoop.thirteen;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class InvertedIndexCombine extends
		Reducer<Text, Text, Text, Text> {
	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		for(Text val:values){
			sum += Integer.parseInt(val.toString());
		}
		
		String[] arr = key.toString().split(":");
		
		context.write(new Text(arr[0]), new Text(arr[1]+":"+sum));
		
	}
}
