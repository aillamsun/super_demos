package hadoop2_6.controlledJobsample.example03;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.17:13
 */
public class GenreMovieListReducer extends Reducer<NullWritable, Text, NullWritable, Text> {
    @Override
    protected void reduce(NullWritable key, Iterable<Text> values,
                          Context context) throws IOException, InterruptedException {
        for (Text movie : values) {
            context.write(NullWritable.get(), new Text(movie));
        }
    }
}