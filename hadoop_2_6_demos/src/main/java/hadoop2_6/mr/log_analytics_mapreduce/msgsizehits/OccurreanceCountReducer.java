package hadoop2_6.mr.log_analytics_mapreduce.msgsizehits;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer calculates the number of occurrences for each messages size
 */
public class OccurreanceCountReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
  public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException,
      InterruptedException {
    int sum = 0;
    for (IntWritable val : values) {
      sum += val.get();
    }
    context.write(key, new IntWritable(sum));
  }
}
