package hadoop2_6.controlledJobsample.example01;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Created by sungang on 2016/1/14.16:02
 */
public class SortingMapper extends
        Mapper<LongWritable, Text, NullWritable, MovieRatingPair> {

    // declare a treemap and collect top 10 average movie for that particular
    // mapper
    private TreeSet<MovieRatingPair> top_10 = new TreeSet<MovieRatingPair>();

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String file_line = value.toString();
        String[] movie = file_line.split("\\t");
        String movie_id = movie[0];
        Double average_ratings = Double.parseDouble(movie[1]);

        if (top_10.size() == 10) {
            top_10.add(new MovieRatingPair(movie_id, average_ratings));
            top_10.remove(top_10.first());
        } else {
            top_10.add(new MovieRatingPair(movie_id, average_ratings));
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException,
            InterruptedException {
        TreeSet<MovieRatingPair> rev_top_10 = new TreeSet<MovieRatingPair>(Collections.reverseOrder());
        rev_top_10.addAll(top_10);
        for (MovieRatingPair a : top_10) {
            System.out.println(a.toString());
            context.write(NullWritable.get(), a);
        }

    }
}
