package com.wy.hadoop.one;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobMain {

	public static void main(String[] args)throws Exception {
		Configuration configuration = new Configuration();
		Job job = new Job(configuration,"max_temperature_job");
		job.setJarByClass(JobMain.class);
		
		job.setMapperClass(NewMaxTemperatureMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setReducerClass(NewMaxTemperatureReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		Path outputDir = new Path(args[1]);
		FileSystem fs = FileSystem.get(configuration);
		if(fs.exists(outputDir)){
			fs.delete(outputDir, true);
			System.out.println("output file is exist,but it has deleted!");
		}
		FileOutputFormat.setOutputPath(job, outputDir);
		
		job.setNumReduceTasks(1);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}

}
