package hadoop2_6.kv_sort.example01;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by sungang on 2016/3/28.11:41
 *
 */
public class SecondarySortReducer extends Reducer<Entry,Text,Entry,Text>{

    @Override
    protected void reduce(Entry key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder builder = new StringBuilder();
        for (Text value : values)
            builder.append(value.toString());
            builder.append(",");

        context.write(key,new Text(builder.toString()));
    }
}
