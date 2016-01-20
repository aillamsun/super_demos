package hadoop2_6.mr.log_analytics_mapreduce.custom;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.StringTokenizer;

/**
 * custom partitioner to partition HTTP log events based on IP Address
 */
public class IPPartitioner extends Partitioner<Text, IntWritable> {

  @Override
  public int getPartition(Text ipAddress, IntWritable value, int numPartitions) {
    StringTokenizer tokenizer  = new StringTokenizer(ipAddress.toString(), ".");
    if (tokenizer.hasMoreTokens()){
      String token = tokenizer.nextToken();
      return ((token.hashCode() & Integer.MAX_VALUE) % numPartitions);
    }
    return 0;
  }
}
