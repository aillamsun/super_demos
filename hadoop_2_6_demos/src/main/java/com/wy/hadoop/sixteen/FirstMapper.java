package com.wy.hadoop.sixteen;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FirstMapper extends Mapper<Text, FirstClass, Text, Text> {

	@Override
	protected void map(Text key, FirstClass value,Context context)
			throws IOException, InterruptedException {
		context.write(key, new Text(value.toString()));
	}

}
