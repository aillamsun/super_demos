package hadoop2_6.mr.log_analytics_mapreduce.statuscounter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

/**
 * Description goes here
 */
public class DriverStatusCounter extends Configured implements Tool {
  @Override
  public int run(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("statuscounter.DriverStatusCounter <inDir> <outDir>");
      ToolRunner.printGenericCommandUsage(System.out);
      System.out.println("");
      return -1;
    }

    System.out.println(Arrays.toString(args));

    Job job = new Job(getConf(), "status counter");
    job.setJarByClass(DriverStatusCounter.class);
    job.setMapperClass(ParseStatusCodeMapper.class);
    // Uncomment this to set combiner
    // job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(StatusCodeAggregateReducer.class);
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.waitForCompletion(true);

    return 0;
  }

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new DriverStatusCounter(), args);
    System.exit(res);
  }
}
