package com.wy.hadoop.eight;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JoinOneMapper extends Mapper<LongWritable, Text, IntWritable, Emplyee> {
	
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] arr = line.split("\t");
		System.out.println("-------"+arr.length);
		if(arr.length<=3){//部门数据 
			Emplyee emplyee = new Emplyee();
			emplyee.setDeptno(arr[0]);
			emplyee.setDname(arr[1]);
			emplyee.setFlag(0);
			
			context.write(new IntWritable(Integer.parseInt(arr[0])), emplyee);
		}else{//员工信息
			Emplyee emplyee = new Emplyee();
			emplyee.setEmpno(arr[0]);
			emplyee.setEname(arr[1]);
			emplyee.setDeptno(arr[7]);
			emplyee.setFlag(1);
			
			context.write(new IntWritable(Integer.parseInt(arr[7])), emplyee);
		}
		
		
	}
	
	public static void main(String[] args){
		String line = "30	sales	chicago";
		String[] arr = line.split("\t");
		System.out.println("-------"+arr.length);
		if(arr.length==3){//部门数据 
			System.out.println("----1");
		}else{//员工信息
			System.out.println("----2");
		}
	}

}
