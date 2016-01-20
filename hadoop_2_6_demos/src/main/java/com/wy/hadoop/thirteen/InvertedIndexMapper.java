package com.wy.hadoop.thirteen;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class InvertedIndexMapper extends
		Mapper<LongWritable, Text, Text, Text> {

	private String fileName;
	private final Text val = new Text("1");
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		if(line.trim().length()>0){
			StringTokenizer tokenizer = new StringTokenizer(line);
			while(tokenizer.hasMoreTokens()){
				String keyTmp = tokenizer.nextToken()+":"+fileName;
				context.write(new Text(keyTmp), val);
			}
		}
	}

	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		FileSplit split = (FileSplit) context.getInputSplit();
		String tmp = split.getPath().toString();//  /user/data/thirteen/thirteen_file1.txt;
		fileName = tmp.substring(tmp.indexOf("file"));
	}
	
	

}
