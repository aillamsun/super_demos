package hadoop2_6.controlledJobsample.example01;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.16:05
 */
public class MovieRatingReducer extends
        Reducer<Text, IntWritable, Text, DoubleWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values,
                          Context context) throws IOException, InterruptedException {
        int sum_of_ratings = 0;
        int count_of_ratings = 0;
        for (IntWritable value : values) {
            sum_of_ratings = sum_of_ratings + value.get();
            count_of_ratings = count_of_ratings + 1;
        }
        context.write(new Text(key), new DoubleWritable((sum_of_ratings * 1.0) / count_of_ratings));
        //System.out.println(key + ":: [" + (sum_of_ratings) + "/" + (count_of_ratings) + "] = " + ((sum_of_ratings * 1.0) / count_of_ratings));
    }
}