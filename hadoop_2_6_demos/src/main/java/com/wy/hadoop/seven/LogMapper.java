package com.wy.hadoop.seven;

import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LogMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private IntWritable val = new IntWritable(1);
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString().trim();
		String tmp = handlerLog(line);
		if(tmp.length()>0){
			context.write(new Text(tmp), val);
		}
	}

	//127.0.0.1 - - [03/Jul/2014:23:36:38 +0800] "GET /course/detail/3.htm HTTP/1.0" 200 38435 0.038
	private String handlerLog(String line){
		String result = "";
		try{
			if(line.length()>20){
				if(line.indexOf("GET")>0){
					result = line.substring(line.indexOf("GET"), line.indexOf("HTTP/1.0")).trim();
				}else if(line.indexOf("POST")>0){
					result = line.substring(line.indexOf("POST"), line.indexOf("HTTP/1.0")).trim();
				}
			}
		}catch (Exception e) {
			System.out.println(line);
		}
		
		return result;
	}
	
	public static void main(String[] args){
		String line = "127.0.0.1 - - [03/Jul/2014:23:36:38 +0800] \"GET /course/detail/3.htm HTTP/1.0\" 200 38435 0.038";
		System.out.println(new LogMapper().handlerLog(line));
	}
}
