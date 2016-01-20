package hadoop2_6.mr.log_analytics_mapreduce.hitsperhour;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  calculates the hour of the day for a log event
 *
 */
public class ParseHourMapper extends Mapper<Object, Text, IntWritable, IntWritable> {

  public static enum LOG_PROCESSOR_COUNTER {
    MALFORMED_RECORDS,
    PROCESSED_RECORDS,
  }

  // 2014-04-18T02:27:25Z
  public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  public static final Pattern httplogPattern = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[(.*)\\] \"([^\\s]+)" +
      " (/[^\\s]*) HTTP/[^\\s]+\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"$");


  private final static IntWritable one = new IntWritable(1);

  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    try {
      Matcher matcher = httplogPattern.matcher(value.toString());
      if (matcher.matches()) {
        //context.getCounter(LOG_PROCESSOR_COUNTER.PROCESSED_RECORDS).increment(1);
        String timeAsStr = matcher.group(4);
        Date time = dateFormatter.parse(timeAsStr);
        // creates a new calendar instance
        Calendar calendar = GregorianCalendar.getInstance();
        // assigns calendar to given date
        calendar.setTime(time);
        // gets hour in 24h format
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        context.write(new IntWritable(hours), one);
      } else {
        //context.getCounter(LOG_PROCESSOR_COUNTER.MALFORMED_RECORDS).increment(1);
      }
    } catch (ParseException e) {
      // we ignore if there are few misformatted entries
      e.printStackTrace();
    }
  }
}
