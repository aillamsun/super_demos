package hadoop2_6.day1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class WordCount extends Configured implements Tool {
	static final String HADOOP_LOCAL = "D:/hadoop/2_6_0";
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new WordCount(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
//		conf.set("mapreduce.input.fileinputformat.split.maxsize", "" + 64*1024*1024);
//		conf.set("mapreduce.input.fileinputformat.split.minsize", "" + 256*1024*1024);
//		System.setProperty("hadoop.home.dir",HADOOP_LOCAL);
//		conf.set("mapreduce.input.fileinputformat.input.dir.recursive", "true");
//		conf.addResource(new Path(HADOOP_LOCAL+"/etc/hadoop/core-site.xml"));
//		conf.addResource(new Path(HADOOP_LOCAL+"/etc/hadoop/hdfs-site.xml"));
//		conf.set("hadoop.job.user", "root");
		Job job = Job.getInstance(conf,WordCount.class.getSimpleName());
		//设置jar
		job.setJarByClass(WordCount.class);
		job.setInputFormatClass(TextInputFormat.class);
		//设置Mapper相关的属性
		job.setMapperClass(WCMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/words"));//
		//设置Reducer相关属性
		job.setReducerClass(WCReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/wordsout2"));
		//job.setCombinerClass(WCReducer.class);
		//提交任务
		job.setOutputFormatClass(TextOutputFormat.class);
		job.waitForCompletion(true);
		return job.isSuccessful() ? 0:1;
	}
}
