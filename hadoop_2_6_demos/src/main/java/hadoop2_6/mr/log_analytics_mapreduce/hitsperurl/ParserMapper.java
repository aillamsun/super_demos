package hadoop2_6.mr.log_analytics_mapreduce.hitsperurl;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * parses the line using regex and emits the url against 1
 */
public class ParserMapper extends Mapper<Object, Text, Text, IntWritable> {

  public static final Pattern httplogPattern = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[(.*)\\] \"([^\\s]+)" +
      " (/[^\\s]*) HTTP/[^\\s]+\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"$");

  public static enum LOG_PROCESSOR_COUNTER {
    MALFORMED_RECORDS,
    PROCESSED_RECORDS
  }

  private final static IntWritable one = new IntWritable(1);
  private Text word = new Text();

  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    Matcher matcher = httplogPattern.matcher(value.toString());
    if (matcher.matches()) {
      //context.getCounter(LOG_PROCESSOR_COUNTER.PROCESSED_RECORDS).increment(1);
      String request = matcher.group(6);
      word.set(request);
      context.write(word, one);
    } else {
      //context.getCounter(LOG_PROCESSOR_COUNTER.MALFORMED_RECORDS).increment(1);
    }
  }
}
