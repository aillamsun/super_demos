package com.wy.hadoop.six;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobMain {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		Configuration configuration = new Configuration();
		configuration.setInt("N", Integer.parseInt(args[2]));
		Job job = new Job(configuration,"topn_job");
		job.setJarByClass(JobMain.class);
		
		job.setMapperClass(TopNMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setReducerClass(TopNReducer.class);
		job.setOutputKeyClass(IntWritable.class);
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
