package hadoop2_6.controlledJobsample.example01;


import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Created by sungang on 2016/1/14.16:04
 */
public class SortingReducer extends Reducer<NullWritable, MovieRatingPair, NullWritable, Text> {
    @Override
    protected void reduce(NullWritable key, Iterable<MovieRatingPair> values,
                          Context context) throws IOException, InterruptedException {
        TreeSet<MovieRatingPair> top_10 = new TreeSet<MovieRatingPair>();

        for (MovieRatingPair pair : values) {
            //System.out.println("r:" + pair.toString());
            if (top_10.size() == 10) {
                top_10.add(new MovieRatingPair(pair));
                top_10.remove(top_10.first());
            } else {
                top_10.add(new MovieRatingPair(pair));
            }

        }
        ArrayList<MovieRatingPair> descending_array = new ArrayList<MovieRatingPair>();
        /*
		for(MovieRatingPair pair: top_10){
			//System.out.println(top_10.size());
			//System.out.println(pair.toString());
			//context.write(NullWritable.get(), new Text(pair.toString()));
			descending_array.add(pair);
		}
		*/
        descending_array.addAll(top_10);
        Collections.reverse(descending_array);
        for (int i = 0; i < descending_array.size() - 1; i++) {
            context.write(NullWritable.get(), new Text(descending_array.get(i).toString()));
        }
    }
}