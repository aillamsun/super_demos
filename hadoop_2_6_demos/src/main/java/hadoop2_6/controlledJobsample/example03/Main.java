package hadoop2_6.controlledJobsample.example03;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Created by sungang on 2016/1/14.17:13
 * 统计输入的电影流派 多个以,分隔
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("parameter", args[2]);
        Job job = new Job(conf, "Genre-Movie-List");

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setJarByClass(Main.class);

        job.setMapperClass(GenreMovieListMapper.class);
        job.setReducerClass(GenreMovieListReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

}
