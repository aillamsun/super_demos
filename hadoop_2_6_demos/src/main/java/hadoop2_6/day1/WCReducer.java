package hadoop2_6.day1;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	protected void reduce(Text key, Iterable<LongWritable> v2s, Context context)
			throws IOException, InterruptedException {
		//定义一行计数器
		long sum = 0;
		//迭代他的次数
		for(LongWritable lw : v2s){
			//求和
			sum += lw.get();
		}
		//输出
		context.write(key, new LongWritable(sum));
	}

	
}
