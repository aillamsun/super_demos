package hadoop2_6.muiltiinputsample.example01;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.14:42
 */
public class SampleMapper extends Mapper<Text,Text,Text,Text> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }
}
