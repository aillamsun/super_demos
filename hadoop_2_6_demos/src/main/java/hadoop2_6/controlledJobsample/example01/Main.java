package hadoop2_6.controlledJobsample.example01;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
/**
 * Created by sungang on 2016/1/14.16:06
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Configuration conf_one = new Configuration();
        Job job_one = new Job(conf_one, "Movie-Average-Finder");
        job_one.setJarByClass(Main.class);

        //Mapper
        job_one.setMapperClass(MovieRatingMapper.class);
        job_one.setMapOutputKeyClass(Text.class);//map阶段的输出的key
        job_one.setMapOutputValueClass(IntWritable.class);//map阶段的输出的value

        //Reduce
        job_one.setReducerClass(MovieRatingReducer.class);
        job_one.setOutputKeyClass(Text.class);//reduce阶段的输出的key
        job_one.setOutputValueClass(DoubleWritable.class);//reduce阶段的输出的value

        job_one.setInputFormatClass(TextInputFormat.class);
        job_one.setOutputFormatClass(TextOutputFormat.class);


        //加入第一个控制容器
        ControlledJob ctrljob_one = new ControlledJob(conf_one);
        //
        ctrljob_one.setJob(job_one);

        //job_one 的输入输出文件路径
        FileInputFormat.addInputPath(job_one, new Path(args[0]));
        FileOutputFormat.setOutputPath(job_one, new Path(args[1]));


        //第二个作业的配置
        Configuration conf_two = new Configuration();
        Job job_two = new Job(conf_one, "Movie-Top10-Finder");
        job_two.setJarByClass(Main.class);

        //Mapper
        job_two.setMapperClass(SortingMapper.class);
        job_two.setMapOutputKeyClass(NullWritable.class);
        job_two.setMapOutputValueClass(MovieRatingPair.class);

        //Reducer
        job_two.setReducerClass(SortingReducer.class);
        job_two.setOutputKeyClass(NullWritable.class);
        job_two.setOutputValueClass(MovieRatingPair.class);

        job_two.setInputFormatClass(TextInputFormat.class);
        job_two.setOutputFormatClass(TextOutputFormat.class);


        //加入第二个控制容器
        ControlledJob ctrljob_two = new ControlledJob(conf_one);
        //
        ctrljob_two.setJob(job_two);
        FileInputFormat.setInputPaths(job_two, new Path(args[1]+"/part-*"));
        FileOutputFormat.setOutputPath(job_two, new Path(args[2]));

        //主的控制容器，控制上面的总的两个子作业
        JobControl jobCtrl = new JobControl("Job-Control-One");

        //添加到总的JobControl里，进行控制
        jobCtrl.addJob(ctrljob_one);
        jobCtrl.addJob(ctrljob_two);

        //设置多个作业直接的依赖关系
        //如下所写：
        //意思为job_two的启动，依赖于job_one作业的完成
        ctrljob_two.addDependingJob(ctrljob_one);

        //jobCtrl.run();
        //jobCtrl.allFinished();

        //在线程启动，记住一定要有这个
        Thread thread = new Thread(jobCtrl);
        thread.start();

        while (true) {
            //如果作业成功完成，就打印成功作业的信息
            if (jobCtrl.allFinished()) {
                System.out.println(jobCtrl.getSuccessfulJobList());
                jobCtrl.stop();
                break;
            }
        }
    }
}
