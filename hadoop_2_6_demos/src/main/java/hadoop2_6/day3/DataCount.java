package hadoop2_6.day3;

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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class DataCount extends Configured implements Tool {
	
	enum Counter{
		LINESIP
	}
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(), new DataCount(), args);
		System.exit(res);
	}
	
	public int run(String[] args) throws Exception {
		System.out.println("data count start..............................................................");
		Configuration configuration = getConf();
		Job job = Job.getInstance(configuration, "data_count_20151104 ");
		job.setJarByClass(DataCount.class);


		job.setMapperClass(DCMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DataBean.class);

		job.setReducerClass(DCReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DataBean.class);

		FileInputFormat.setInputPaths(job, new Path("/datacount.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/datacount2.out"));
		job.waitForCompletion(true);
		return job.isSuccessful() ? 0 : 1;
	}
	
	
	public static class DCMapper extends
			Mapper<LongWritable, Text, Text, DataBean> {
		private DataBean dataBean = null;
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			//获取一行内容
			try {
				String lineData = value.toString();
				String[] lineDataArr = lineData.split("\t");
				String phoneNo = lineDataArr[0].trim();
				String upPayLoad = lineDataArr[1].trim();
				String downPayLoad = lineDataArr[2].trim();
				dataBean = new DataBean(phoneNo, Long.parseLong(upPayLoad), Long.parseLong(downPayLoad));
				context.write(new Text(phoneNo), dataBean);
			} catch (Exception e) {
				//遇到错误  跳过这一行
				context.getCounter(Counter.LINESIP).increment(1);
			}
		}
	}
	
	public static class DCReduce extends Reducer<Text, DataBean, Text, DataBean>{
		@Override
		protected void reduce(Text key, Iterable<DataBean> values,Context context)
				throws IOException, InterruptedException {
			long up_sum = 0;
			long down_sum = 0;
			for (DataBean dataBean : values) {
				up_sum += dataBean.getUpPayLoad();
				down_sum += dataBean.getDownPayLoad();
			}
			DataBean dataBean = new DataBean("",up_sum,down_sum);
			context.write(key, dataBean);
		}
	}

}
