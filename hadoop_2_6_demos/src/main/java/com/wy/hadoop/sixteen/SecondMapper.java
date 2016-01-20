package com.wy.hadoop.sixteen;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SecondMapper extends Mapper<Text, SecondClass, Text, Text> {

	@Override
	protected void map(Text key, SecondClass value,Context context)
			throws IOException, InterruptedException {
		context.write(key, new Text(value.toString()));
	}

	
}
