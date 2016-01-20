package com.wy.hadoop.ten;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinThreeReducer extends Reducer<UserKey, User, NullWritable, Text> {

	@Override
	protected void reduce(UserKey key, Iterable<User> values,Context context)
			throws IOException, InterruptedException {
		User user = null;
		int num = 0;
		for(User u: values){
			if(num==0){
				user = new User(u);
				num++;
			}else{
				u.setCityName(user.getCityName());
				context.write(NullWritable.get(), new Text(u.toString()));
			}
		}
		
		
	}

}
