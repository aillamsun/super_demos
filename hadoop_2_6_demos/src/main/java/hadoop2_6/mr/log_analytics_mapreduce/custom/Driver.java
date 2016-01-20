package hadoop2_6.mr.log_analytics_mapreduce.custom;

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
 * 统计日志请求URL sum
 * [root@master /]# hadoop fs -cat /tmp/logs/out/custom/part-r-00000
 * //images/stories/3xp.php	8
 * //images/stories/cr0t.php	1
 * //register.aspx?agree=yes	1
 * /2013/06/s	1
 * /2013/07/httpd%EF%BF%BDA%EF%BF%BDn0%EF%BF%BD%EF%BF%BD3%EF%BF%BDoep	1
 * /2013/08/%EF%BF%BDcthag	1
 * /2013/08/ht8%EF%BF%BD;n	1
 * /3%EF%BF%BD	1
 * /CreateUser.asp	1
 * /HNAP1/	1
 * /PMA/	1
 * /account/register.php	1
 * /admin.php	2
 *
 * hadoop jar /usr/log.jar hadoop2_6.mr.log_analytics_mapreduce.custom.Driver /tmp/logs/log_kpi /tmp/logs/out/custom 2
 */
public class Driver extends Configured implements Tool {
  @Override
  public int run(String[] args) throws Exception {
    if (args.length < 3) {
      System.out.println("custom.Driver <inDir> <outDir> <numReducers>");
      ToolRunner.printGenericCommandUsage(System.out);
      System.out.println("");
      return -1;
    }

    System.out.println(Arrays.toString(args));

    Job job = new Job(getConf(), "custom log analysis");
    job.setJarByClass(Driver.class);
    job.setMapperClass(CustomMapper.class);
    job.setReducerClass(CustomReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    job.setInputFormatClass(HTTPLogInputFormat.class);
    job.setPartitionerClass(UrlPartitioner.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.setNumReduceTasks(Integer.parseInt(args[2]));
    job.waitForCompletion(true);

    return 0;
  }

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new Driver(), args);
    System.exit(res);
  }
}
