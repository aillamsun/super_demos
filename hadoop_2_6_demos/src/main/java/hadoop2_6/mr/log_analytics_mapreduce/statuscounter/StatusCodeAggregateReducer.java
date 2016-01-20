package hadoop2_6.mr.log_analytics_mapreduce.statuscounter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * calculates number of times a request has been rendered successful (200), failed (400), permission denied(503)
 */
public class StatusCodeAggregateReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
  public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
    int sum = 0;
    for (IntWritable val : values) {
      sum += val.get();
    }
    context.write(key, new IntWritable(sum));
  }
}