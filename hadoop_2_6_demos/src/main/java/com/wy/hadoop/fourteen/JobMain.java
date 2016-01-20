package com.wy.hadoop.fourteen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class JobMain {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		Configuration configuration = new Configuration();
		Job job = new Job(configuration,"many_column_sort_job");
		job.setJarByClass(JobMain.class);
		
		job.setMapperClass(ManyColumnMapper.class);
		job.setMapOutputKeyClass(IntPair.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setPartitionerClass(SelfPartitioner.class);
		job.setSortComparatorClass(IntSortComparator.class);
		
		job.setReducerClass(ManyColumnReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setGroupingComparatorClass(GroupingComparator.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
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
