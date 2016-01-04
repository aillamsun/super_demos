package hadoop2_6.log.kpi;

import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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

import com.google.common.collect.Sets;

public class MergeExample extends Configured implements Tool {

	public static class MergeExampleMapper extends
			Mapper<LongWritable, Text, Text, NullWritable> {
		
		Text text = new Text();
		LongWritable one = new LongWritable(1);
		
		@Override
		public void setup(Context context)
				throws IOException, InterruptedException {
			System.out.println("*************************任务开始了**************************");
		}
		
		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String fields[] = value.toString().split("\\s");
			Set<String> fieldSet = Sets.newHashSet();
			for (String field : fields) {
				fieldSet.add(field);
			}
			text.set(fieldSet.toString());
		}

		
		@Override
		public void cleanup(Context context) throws IOException,
				InterruptedException {
			System.out.println("*********************************cleanup*********************");
			context.write(text, NullWritable.get());
			//System.out.println("**********************************aa**************************");
		}
	}

//	public static class MergeExampleReducer extends
//			Reducer<Text, LongWritable, Text, LongWritable> {
//		private LongWritable result = new LongWritable();
//
//		@Override
//		public void reduce(Text key, Iterable<LongWritable> values,
//				Context context) throws IOException, InterruptedException {
//
//		}
//	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new MergeExample(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		String input = "hdfs://master:9000/tmp/logs/merge_data/";
		String output = "hdfs://master:9000/tmp/logs/merge_data/result";

		Configuration conf = getConf();
		Job job = Job.getInstance(conf, MergeExample.class.getSimpleName());

		// 设置jar
		job.setJarByClass(MergeExample.class);
		job.setInputFormatClass(TextInputFormat.class);
		// 设置Mapper相关的属性
		job.setMapperClass(MergeExampleMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, input);//
		// 设置Reducer相关属性
		//job.setReducerClass(MergeExampleReducer.class);
		//job.setOutputKeyClass(Text.class);
		//job.setOutputValueClass(LongWritable.class);

		FileOutputFormat.setOutputPath(job, new Path(output));
		// 提交任务
		job.setOutputFormatClass(TextOutputFormat.class);
		job.waitForCompletion(true);
		return job.isSuccessful() ? 0 : 1;
	}

}
