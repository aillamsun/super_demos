package com.wy.hadoop.ten;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinThreeMapper extends Mapper<LongWritable, Text, UserKey, User> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		String[] arr = line.split("\t");
		if(arr.length==2){//city
			User user = new User();
			user.setCityNo(arr[0]);
			user.setCityName(arr[1]);
			
			UserKey uKey = new UserKey();
			uKey.setCityNo(Integer.parseInt(arr[0]));
			uKey.setPrimary(true);
			
			context.write(uKey, user);
		}else{//user
			User user = new User();
			user.setUserNo(arr[0]);
			user.setUserName(arr[1]);
			user.setCityNo(arr[2]);
			
			UserKey uKey = new UserKey();
			uKey.setCityNo(Integer.parseInt(arr[2]));
			uKey.setPrimary(false);
			
			context.write(uKey, user);
		}
		
	}

}
