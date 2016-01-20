package hadoop2_6.controlledJobsample.example02;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.17:08
 */
public class MovieGenreMapper extends
        Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String file_line = value.toString();
        String[] first_split = file_line.split("\\::");
        String[] second_split = first_split[2].split("\\|");
        for (String genre : second_split) {
            context.write(new Text(genre), new IntWritable(1));
        }
    }

}