package hadoop2_6.mr.log_analytics_mapreduce.custom;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mapper using custom LogWritable data type, emits url if statusCode is 404
 */
public class CustomMapper extends Mapper<Object, LogWritable, Text, IntWritable> {

  private final static IntWritable one = new IntWritable(1);

  public void map(Object key, LogWritable value, Context context) throws IOException, InterruptedException {
    if (value.getStatus().get() == 404) {
      context.write(value.getRequestPage(), one);
    }
  }

}
