package com.wy.hadoop.nine;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinTwoMapper extends Mapper<LongWritable, Text, IntWritable, User> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] arr = line.split("\t");
		if(arr.length==2){//城市信息
			User user = new User();
			user.setCityNo(arr[0]);
			user.setCityName(arr[1]);
			user.setFlag(0);
			context.write(new IntWritable(Integer.parseInt(arr[0])), user);
		}else{//人员信息
			User user = new User();
			user.setUserNo(arr[0]);
			user.setUserName(arr[1]);
			user.setCityNo(arr[2]);
			user.setFlag(1);
			
			context.write(new IntWritable(Integer.parseInt(arr[2])), user);
			
		}
	}

}
