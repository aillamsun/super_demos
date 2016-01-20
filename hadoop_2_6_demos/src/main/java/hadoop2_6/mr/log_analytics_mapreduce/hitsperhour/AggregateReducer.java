package hadoop2_6.mr.log_analytics_mapreduce.hitsperhour;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Aggregates the occurrences per hour
 */
public class AggregateReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
  public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException,
      InterruptedException {
    int sum = 0;
    for (IntWritable val : values) {
      sum += val.get();
    }
    context.write(key, new IntWritable(sum));
  }
}
