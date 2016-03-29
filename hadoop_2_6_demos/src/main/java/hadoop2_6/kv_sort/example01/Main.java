package hadoop2_6.kv_sort.example01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by sungang on 2016/3/28.11:44
 */
public class Main {

    public static void main(String[] args) throws  Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJobName("SecondarySort");
        job.setJarByClass(Main.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        job.setMapOutputKeyClass(Entry.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Entry.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(SecondarySortMapper.class);
        job.setReducerClass(SecondarySortReducer.class);
        job.setPartitionerClass(EntryPartitioner.class);
        job.setGroupingComparatorClass(EntryGroupingComparator.class);
        job.waitForCompletion(true);
    }

}
