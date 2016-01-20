package com.wy.hadoop.eleven;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SecondarySortMapper extends
		Mapper<LongWritable, Text, IntPair, Text> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] arr = line.split("\t");
		if(arr.length==3){
			IntPair tmp = new IntPair(arr[0],arr[1]);
			context.write(tmp, value);
		}
	}
	
}
