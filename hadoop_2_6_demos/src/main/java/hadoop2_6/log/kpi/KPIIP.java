package hadoop2_6.log.kpi;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
/**
* @description: 页面独立IP的访问量统计
* @author: sungang
* @date: 2015年12月15日 上午11:05:37
 */
public class KPIIP extends Configured implements Tool{

	public static class KPIIPMapper extends Mapper<LongWritable, Text, Text, Text>{
		private Text word = new Text();
		private Text ips = new Text();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			KPI kpi = KPI.filterIPs(value.toString());
			if (kpi.isValid()) {
				word.set(kpi.getRequest());
				ips.set(kpi.getRemote_addr());
				context.write(word, ips);
			}
		}
	}
	
	
	public static class KPIIPReducer extends Reducer<Text, Text, Text, Text>{
		private Text result = new Text();
		private Set<String> count = new HashSet<String>();
		@Override
		protected void reduce(Text key, Iterable<Text> values,Context context)
				throws IOException, InterruptedException {
			for(Text value : values){
				//求和
				count.add(value.toString());
			}
			result.set(String.valueOf(count.size()));
			context.write(key, result);
		}
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new KPIIP(), args);
		System.exit(res);
	}
	
	public int run(String[] arg) throws Exception {
		String input = "hdfs://master:9000/tmp/logs/log_kpi/";
		String output = "hdfs://master:9000/tmp/logs/log_kpi/ips";

		Configuration conf = getConf();
		//conf.addResource("");
		
		
		Job job = Job.getInstance(conf, KPIIP.class.getSimpleName());

		// 设置jar
		job.setJarByClass(KPIIP.class);
		job.setInputFormatClass(TextInputFormat.class);
		// 设置Mapper相关的属性
		job.setMapperClass(KPIIPMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, input);//
		// 设置Reducer相关属性
		job.setReducerClass(KPIIPReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileOutputFormat.setOutputPath(job, new Path(output));
		// 提交任务
		job.setOutputFormatClass(TextOutputFormat.class);
		job.waitForCompletion(true);
		
		return job.isSuccessful() ? 0 : 1;
	}
	
}
