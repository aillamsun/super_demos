package hadoop2_6.day1;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<Text, Text, Text, LongWritable> {

    @Override
    protected void map(Text key, Text value, Context context)
            throws IOException, InterruptedException {
        //接收一行数据
        String line = value.toString();
        //分割
        String[] words = line.split("\t");
        //迭代
        for (String w : words) {
            //发送

            context.write(new Text(w), new LongWritable(1));
        }
    }
}
