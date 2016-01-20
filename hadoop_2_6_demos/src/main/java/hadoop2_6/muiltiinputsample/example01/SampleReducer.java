package hadoop2_6.muiltiinputsample.example01;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by sungang on 2016/1/14.14:44
 */
public class SampleReducer extends Reducer<Text,Text,Text,Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        int totalAmount = 0;
        for (Text value : values) {
            String valueString = value.toString();
            int currentAmount = 0;
            try {
                currentAmount = Integer.parseInt(valueString);
            } catch (NumberFormatException exception) {
                // If you have your own loggin system log it here.
                // logger.error("Blah blah blah");
            }

            totalAmount += currentAmount;
        }
        context.write(key, new Text(Integer.toString(totalAmount)));
    }
}
