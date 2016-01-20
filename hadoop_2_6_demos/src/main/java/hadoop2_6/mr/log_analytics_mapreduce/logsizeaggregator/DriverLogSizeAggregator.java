package hadoop2_6.mr.log_analytics_mapreduce.logsizeaggregator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

/**
 * Aggregates the web log messages size
 *
 * hadoop jar /usr/log.jar hadoop2_6.mr.log_analytics_mapreduce.logsizeaggregator.DriverLogSizeAggregator /tmp/logs/log_kpi /tmp/logs/out/logsizeaggregator
 */
public class DriverLogSizeAggregator extends Configured implements Tool {
  @Override
  public int run(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("logsizeaggregator.DriverLogSizeAggregator <inDir> <outDir>");
      ToolRunner.printGenericCommandUsage(System.out);
      System.out.println("");
      return -1;
    }

    System.out.println(Arrays.toString(args));

    Job job = new Job(getConf(), "log size aggregator");
    job.setJarByClass(DriverLogSizeAggregator.class);
    job.setMapperClass(ParseMsgSizeMapper.class);
    // Uncomment this to set combiner
    // job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(SizeAggregateReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.waitForCompletion(true);

    return 0;
  }

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new DriverLogSizeAggregator(), args);
    System.exit(res);
  }
}
