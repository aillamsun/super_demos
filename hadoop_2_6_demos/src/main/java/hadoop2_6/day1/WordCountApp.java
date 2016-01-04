package hadoop2_6.day1;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

public class WordCountApp {
		static final String INPUT_PATH = "hdfs://master:9000/words/";
		static final String OUT_PATH = "hdfs://master:9000/wordsout3";
		static final String HADOOP_LOCAL = "D:/hadoop/2_6_0";
		
		public static void main(String[] args) throws Exception {
			 
			System.setProperty("hadoop.home.dir",HADOOP_LOCAL);
			Configuration conf = new Configuration();
			conf.set("mapreduce.input.fileinputformat.input.dir.recursive", "true");
			conf.addResource(new Path(HADOOP_LOCAL+"/etc/hadoop/core-site.xml"));
			conf.addResource(new Path(HADOOP_LOCAL+"/etc/hadoop/hdfs-site.xml"));
			conf.set("hadoop.job.user", "root");
			
			
			FileSystem fileSystem = FileSystem.get(new URI(OUT_PATH), conf);
			if (fileSystem.exists(new Path(OUT_PATH))){
				fileSystem.delete(new Path(OUT_PATH), true);
			}
//			final Job job = new Job(conf, WordCountApp.class.getSimpleName());  
			final Job job = Job.getInstance(conf, WordCountApp.class.getSimpleName());

			FileInputFormat.setInputPaths(job, INPUT_PATH);

			job.setInputFormatClass(TextInputFormat.class);	
			job.setMapperClass(MyMapper.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(LongWritable.class);	
			job.setPartitionerClass(HashPartitioner.class);
			job.setNumReduceTasks(2);
			job.setReducerClass(MyReducer.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);

			FileOutputFormat.setOutputPath(job, new Path(OUT_PATH));

			job.setOutputFormatClass(TextOutputFormat.class);
			
			job.waitForCompletion(true);
		}

		static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
			protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable,Text,Text,LongWritable>.Context context) throws java.io.IOException ,InterruptedException {
				final String[] splited = value.toString().split("\t");
				for (String word : splited) {
					context.write(new Text(word), new LongWritable(1));
				}
			};
		}
		
		static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
			protected void reduce(Text k2, Iterable<LongWritable> v2s, org.apache.hadoop.mapreduce.Reducer<Text,LongWritable,Text,LongWritable>.Context context) throws java.io.IOException ,InterruptedException {
				long sum = 0L;
				for (LongWritable v2 : v2s) {
					sum += v2.get();
				}
				context.write(k2, new LongWritable(sum));
			};
		}

	}