package com.wy.hadoop.twelve;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SelfJoinReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		List<String> grandChildList = new ArrayList<String>();
		List<String> grandParentList = new ArrayList<String>();
		
		for(Text val:values){
			String[] arr = val.toString().split("_");
			if("1".equals(arr[0])){
				grandChildList.add(arr[1]);
			}else if("2".equals(arr[0])){
				grandParentList.add(arr[1]);
			}
		}
		
		for(String gc:grandChildList){
			for(String gp:grandParentList){
				context.write(new Text(gc), new Text(gp));
			}
		}
		
	}
	
}
