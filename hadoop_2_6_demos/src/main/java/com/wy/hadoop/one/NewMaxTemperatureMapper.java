package com.wy.hadoop.one;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 0067011990999991950051507004888888880500001N9+00781+9999999999999999999999

 数据说明：
 第15-19个字符是year
 第45-50位是温度表示，+表示零上 -表示零下，且温度的值不能是9999，9999表示异常数据
 第50位值只能是0、1、4、5、9几个数字
 * @author Administrator
 *
 */
public class NewMaxTemperatureMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {
	private static final int FAIL_DATA=9999;
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String year = line.substring(15, 19);

		String tmp = line.substring(45, 46);
		int val = 0;
		if("+".equals(tmp)){
			val = Integer.valueOf(line.substring(46,50));
		}else{
			val = Integer.valueOf(line.substring(45,50));
		}
		if(val!=FAIL_DATA && line.substring(50, 51).matches("[01459]")){
			context.write(new Text(year), new IntWritable(val));
		}

	}

	public static void main(String[] args)throws Exception{
		String line = "0067011990999991950051507004888888880500001N9-01003+9999999999999999999999";
		String year = line.substring(15, 19);

		String tmp = line.substring(45, 46);
		int val = 0;
		if("+".equals(tmp)){
			val = Integer.valueOf(line.substring(46,50));
		}else{
			val = Integer.valueOf(line.substring(45,50));
		}
		if(val!=FAIL_DATA && line.substring(50, 51).matches("[01459]")){
			System.out.println("year="+year+"  value="+val);
		}
	}

}
