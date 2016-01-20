package hadoop2_6.mr.log_analytics_mapreduce.resolveip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description goes here
 *
 * @author ashrith
 */
public class ResolveIPMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Logger logger = LoggerFactory.getLogger(ResolveIPMapper.class);

    public static final Pattern httplogPattern = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[(.*)\\] \"([^\\s]+)" +
          " (/[^\\s]*) HTTP/[^\\s]+\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"$");
    DatabaseReader reader;
    private static final int NUM_FIELDS = 10;
    private final static IntWritable one = new IntWritable(1);
    private Text clientIp = new Text();

    @Override
    protected void setup(Context context) throws IOException {
        logger.info("In Mapper's setup method");
        Configuration c = context.getConfiguration();
        String geoipFileName = c.get("geoip.filename");
        File file = new File(geoipFileName);
        reader = new DatabaseReader.Builder(file).build();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException {
        Matcher matcher = httplogPattern.matcher(value.toString());
        if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
            logger.warn("Unable to parse input");
        } else {
            String ip = matcher.group(1);
            try {
                CityResponse response = reader.city(InetAddress.getByName(ip));
                StringBuilder city = new StringBuilder();
                String cityName = response.getCity().getName();
                String countryName = response.getCountry().getName();
                if (cityName != null && cityName.length() != 0) {
                    city.append(cityName);
                    city.append(", ");
                }
                if (countryName != null && countryName.length() != 0) {
                    city.append(countryName);
                }

                clientIp.set(city.toString());
                context.write(clientIp, one);
            } catch (GeoIp2Exception e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
