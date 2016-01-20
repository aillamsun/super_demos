package com.wy.hadoop.two;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.conf.Configuration;

public class JobMain {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception{
		Configuration configuration = new Configuration();
		Job job= new Job(configuration, "de-job");
		job.setJarByClass(JobMain.class);
		
		job.setMapperClass(DeMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setReducerClass(DeReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		Path outputDir = new Path(args[1]);
		FileSystem fs = FileSystem.get(configuration);
		if(fs.exists(outputDir)){
			fs.delete(outputDir, true);
		}
		FileOutputFormat.setOutputPath(job, outputDir);
		job.setNumReduceTasks(1);
		
		System.exit(job.waitForCompletion(true)?0:1);
		

	}

}
