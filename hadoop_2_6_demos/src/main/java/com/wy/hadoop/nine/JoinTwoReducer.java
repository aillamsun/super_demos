package com.wy.hadoop.nine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class JoinTwoReducer extends Reducer<IntWritable, User, NullWritable, Text> {

	@Override
	protected void reduce(IntWritable key, Iterable<User> values,Context context)
			throws IOException, InterruptedException {
		User city = null;
		List<User> list = new ArrayList<User>();
		for(User u:values){
			if(u.getFlag()==0){
				city = new User(u);
			}else{
				list.add(new User(u));
			}
		}
		
		for(User u: list){
			u.setCityName(city.getCityName());
			context.write(NullWritable.get(), new Text(u.toString()));
		}
		
	}

}
