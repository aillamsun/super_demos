package hadoop2_6.mr.log_analytics_mapreduce.resolveip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

/**
 * MapReduce driver for resolveing ip to location
 *
 * Running:
 * 1. Add src/main/resources/GeoLite2-City.mmdb to hdfs
 *  NOTE: driver expects the GeoLite2-City.mmdb in the root of hdfs (/)
 *
 *  hadoop jar /usr/log.jar hadoop2_6.mr.log_analytics_mapreduce.resolveip.Driver /tmp/logs/log_kpi /tmp/logs/out/resolveip
 */
public class Driver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output> \n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        Configuration conf = getConf();
        conf.set("geoip.filename", "GeoLite2-City.mmdb");

        Job job = Job.getInstance(conf);
        job.setJobName("Log Parser");
        job.setJarByClass(getClass());
       // job.addCacheFile(new URI(conf.get("fs.defaultFS") + "/" + conf.get("geoip.filename") + "#" + conf.get("geoip.filename")));



//        URI[] cacheFiles = job.getCacheFiles();
//        if (cacheFiles != null) {
//            for (URI cachefile: cacheFiles) {
//                System.out.println("Cache file ->" + cachefile);
//            }
//        }

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(ResolveIPMapper.class);
        job.setReducerClass(IntSumReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] argv) throws Exception {
        System.exit(ToolRunner.run(new Driver(), argv));
    }
}
