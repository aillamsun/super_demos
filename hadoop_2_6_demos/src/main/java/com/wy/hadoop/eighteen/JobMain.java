package com.wy.hadoop.eighteen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobMain {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		Configuration configuration = new Configuration();
		Job job = new Job(configuration,"chain_job");
		job.setJarByClass(JobMain.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		ChainMapper.addMapper(job, Mapper1.class, LongWritable.class, Text.class, Text.class, IntWritable.class, new Configuration());
		ChainMapper.addMapper(job, Mapper2.class, Text.class, IntWritable.class, Text.class, IntWritable.class, new Configuration());
		
		ChainReducer.setReducer(job, ReduceOnly.class,  Text.class, IntWritable.class, Text.class, IntWritable.class, new Configuration());
		ChainReducer.addMapper(job, Mapper3.class, Text.class, IntWritable.class, Text.class, IntWritable.class, new Configuration());
		
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		Path path = new Path(args[1]);
		FileSystem fs = FileSystem.get(configuration);
		if(fs.exists(path)){
			fs.delete(path, true);
		}
		FileOutputFormat.setOutputPath(job, path);
		
		System.exit(job.waitForCompletion(true)?0:1);

	}

}
