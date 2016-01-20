package hadoop2_6.controlledJobsample.example01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.16:04
 */
public class MovieRatingMapper extends
        Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String file_line = value.toString();
        String[] first_split = file_line.split("\\::");
        context.write(new Text(first_split[1]),new IntWritable(Integer.parseInt(first_split[2])));
        //System.out.println(first_split[1] + "::" + first_split[2] + "\n");
    }

}