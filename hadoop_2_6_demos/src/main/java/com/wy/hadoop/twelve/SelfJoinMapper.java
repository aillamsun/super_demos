package com.wy.hadoop.twelve;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SelfJoinMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		if(line.trim().length()>0){
			String[] arr = line.split("\t");
			if(arr.length==2){
				context.write(new Text(arr[1].trim()), new Text("1_"+arr[0].trim()));//left
				context.write(new Text(arr[0].trim()), new Text("2_"+arr[1].trim()));//right
			}
		}
		
	}

}
