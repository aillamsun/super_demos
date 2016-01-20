package hadoop2_6.mr.log_analytics_mapreduce.custom;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Custom partitioner to partition HTTP log events based on url
 *
 * @author ashrith
 */
public class UrlPartitioner extends Partitioner<Text, IntWritable> {

  @Override
  public int getPartition(Text urlString, IntWritable value, int numReduceTasks) {
    String url = urlString.toString();
    if (!url.isEmpty()) {
      return ((url.hashCode() & Integer.MAX_VALUE) % numReduceTasks);
    }
    return 0;
  }
}
