package com.wy.hadoop.seventeen;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MOReduce extends Reducer<Text, Text, NullWritable, Text> {

	private MultipleOutputs<NullWritable, Text> mos = null;
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		mos.close();
	}

	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		for(Text val:values){
			mos.write("KeySplit", NullWritable.get(), val, key.toString()+"/");
			mos.write("AllData", NullWritable.get(), val);
		}
	}

	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		mos = new MultipleOutputs<NullWritable, Text>(context);
	}

	
}
