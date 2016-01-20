package com.wy.hadoop.six;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 
 * @author Administrator
 *
 */
public class TopNMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

	int len;
	int[] top;
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString().trim();
		if(line.length()>0){//1,9819,100,121
			String[] arr = line.split(",");
			if(arr.length==4){
				int payment = Integer.parseInt(arr[2]);
				add(payment);
			}
		}
	}
	
	private void add(int payment){
		top[0] = payment;
		Arrays.sort(top);
	}
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		len = context.getConfiguration().getInt("N", 10);
		top = new int[len+1];//0,0,0,0,0.....
	}
	
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		for(int i =1;i<len+1;i++){
			context.write(new IntWritable(top[i]), new IntWritable(top[i]));
		}
		
	}
	
	
	public static void main(String[] args){
		int[] tmp = new int[6];
		tmp[0]=10;
		for(int x:tmp){
			System.out.print(x+"  ");
		}
		Arrays.sort(tmp);//3 8 9 10 11 12  tmp[0] 10  ---> 8 9 10 10 11 12-->15 -->9 10 10 11 12 15
		System.out.println("------------");
		for(int x:tmp){
			System.out.print(x+"  ");
		}
	}
}
