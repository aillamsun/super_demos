package hadoop2_6.controlledJobsample.example03;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sungang on 2016/1/14.17:12
 */
public class GenreMovieListMapper extends
        Mapper<LongWritable, Text, NullWritable, Text> {
    String input_parameter = "";

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        input_parameter = conf.get("parameter");

        String file_line = value.toString();
        String[] first_split = file_line.split("\\::");
        //获取电影名
        String movie_name = first_split[1];
        //获取电影流派
        String[] file_movie_genre = first_split[2].split("\\|");

        Set<String> file_movie_genre_set = new TreeSet<String>();
        for (String entry : file_movie_genre) {
            file_movie_genre_set.add(entry);
        }

        String[] input_movie_genre = input_parameter.split("\\,");
        Set<String> input_movie_genre_set = new TreeSet<String>();

        for (String entry : input_movie_genre) {
            if (entry.equals("Children")) {
                entry = entry + "'s";
                input_movie_genre_set.add(entry);
            } else {
                input_movie_genre_set.add(entry);
            }
        }

        if ((file_movie_genre_set.containsAll(input_movie_genre_set))) {
            context.write(NullWritable.get(), new Text(movie_name + " , " + file_line));
        }
    }
}
