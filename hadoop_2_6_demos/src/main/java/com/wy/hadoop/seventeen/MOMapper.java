package com.wy.hadoop.seventeen;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MOMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString().trim();
		if(line.length()>0){
			String[] arr = line.split(",");
			context.write(new Text(arr[0].trim()), value);
		}
	}

}
