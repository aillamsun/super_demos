package com.wy.hadoop.fifteen;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MultipleMapper2 extends Mapper<LongWritable, Text, Text, FlagStringDataType> {

	private String delimiter;
	
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString().trim();
		if(line.length()>0){
			String[] arr = line.split(delimiter);
			if(arr.length==2){
				context.write(new Text(arr[1].trim()), new FlagStringDataType(arr[0].trim(),1));
			}
		}
		
		
	}

	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		delimiter = context.getConfiguration().get("delimiter",",");
	}
	
}
