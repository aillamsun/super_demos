package com.wy.hadoop.eleven;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SecondarySortReducer extends
		Reducer<IntPair, Text, NullWritable, Text> {
	private static Text SEP = new Text("-----------------------------------");
	@Override
	protected void reduce(IntPair key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		context.write(NullWritable.get(), SEP);
		for(Text val:values){
			context.write(NullWritable.get(), val);
		}
	}

}
