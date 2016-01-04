package hadoop2_6.log.kpi;

import java.io.IOException;

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
* @description: 用户的访问设备统计
* @author: sungang
* @date: 2015年12月15日 上午11:05:16
 */
public class KPIBrowser extends Configured implements Tool{
	
	
	public static class KPIBrowserMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		private LongWritable one = new LongWritable(1);
        private Text word = new Text();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			KPI kpi = KPI.filterBroswer(value.toString());
            if (kpi.isValid()) {
                word.set(kpi.getHttp_user_agent());
                context.write(word, one);
            }
		}
	}
	
	public static class KPIBrowserReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		private LongWritable result = new LongWritable();
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Context context)
				throws IOException, InterruptedException {
			long sum = 0;
			for(LongWritable value : values){
				//求和
				sum += value.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new KPIBrowser(), args);
		System.exit(res);
	}
	
	public int run(String[] arg0) throws Exception {
		String input = "hdfs://master:9000/tmp/logs/log_kpi/";
		String output = "hdfs://master:9000/tmp/logs/log_kpi/browser";

		Configuration conf = getConf();
		//conf.addResource("");
		
		
		Job job = Job.getInstance(conf, KPIBrowser.class.getSimpleName());

		// 设置jar
		job.setJarByClass(KPIBrowser.class);
		job.setInputFormatClass(TextInputFormat.class);
		// 设置Mapper相关的属性
		job.setMapperClass(KPIBrowserMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, input);//
		// 设置Reducer相关属性
		job.setReducerClass(KPIBrowserReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		FileOutputFormat.setOutputPath(job, new Path(output));
		// 提交任务
		job.setOutputFormatClass(TextOutputFormat.class);
		job.waitForCompletion(true);
		
		return job.isSuccessful() ? 0 : 1;
	}

}
