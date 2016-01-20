package hadoop2_6.mr.log_analytics_mapreduce.logsizeaggregator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * calculates maximum, minimum and mean file size of the file downloaded from web server
 */
public class SizeAggregateReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
    double tot = 0;
    int count = 0;
    int min = Integer.MAX_VALUE;
    int max = 0;
    Iterator<IntWritable> iterator = values.iterator();

    while (iterator.hasNext()) {
      int value = iterator.next().get();
      tot = tot + value;
      count++;
      if (value < min) {
        min = value;
      }
      if (value > max) {
        max = value;
      }
    }

    context.write(new Text("Mean"), new IntWritable((int) tot / count));
    context.write(new Text("Max"), new IntWritable(max));
    context.write(new Text("Min"), new IntWritable(min));
  }
}
