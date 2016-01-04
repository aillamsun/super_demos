package hadoop2_6.inverted.index;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InverseIndex {

	public static void main(String[] args) throws Exception{
		String input = "hdfs://master:9000/tmp/logs/index/";
		String output = "hdfs://master:9000/tmp/logs/index/result";
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"inverse Index");
		// 设置jar
		job.setJarByClass(InverseIndex.class);
		// 设置Mapper相关的属性
		job.setMapperClass(IndexMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(input));// words.txt
		// 设置Reducer相关属性
		job.setReducerClass(IndexReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(output));
		job.setCombinerClass(IndexCombiner.class);
		// 提交任务
		job.waitForCompletion(true);
	}

	public static class IndexMapper extends
			Mapper<LongWritable, Text, Text, Text> {
		private Text k = new Text();
		private Text v = new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String line = value.toString();
			String words[] = line.split(" ");
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			Path path = inputSplit.getPath();
			String name = path.getName();
			for (String word : words) {
				k.set(word + "->" + name);
				v.set("1");
				context.write(k, v);
			}
		}
	}

	public static class IndexCombiner extends Reducer<Text, Text, Text, Text> {

		private Text k = new Text();
		private Text v = new Text();

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String[] words = key.toString().split("->");
			long sum = 0;
			for (Text word : values) {
				sum += Long.parseLong(word.toString());
			}
			k.set(words[0]);
			v.set(words[1] + "->" + sum);
			context.write(k, v);
		}
	}

	public static class IndexReducer extends Reducer<Text, Text, Text, Text> {
		private Text v = new Text();

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String value = "";
			for (Text t : values) {
				value += t.toString() + " ";
			}
			v.set(value);
			context.write(key, v);
		}
	}
}
