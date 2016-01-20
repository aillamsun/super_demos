package hadoop2_6.controlledJobsample.example02;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.17:09
 */
public class MovieGenreReducer extends
        Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values,
                          Context context) throws IOException, InterruptedException {
        int sum_of_movies = 0;
        for (IntWritable value : values) {
            sum_of_movies = sum_of_movies + value.get();
        }
        context.write(key, new IntWritable(sum_of_movies));
    }

}