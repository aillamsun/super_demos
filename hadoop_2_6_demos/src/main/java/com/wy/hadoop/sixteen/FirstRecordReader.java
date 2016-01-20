package com.wy.hadoop.sixteen;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class FirstRecordReader extends RecordReader<Text, FirstClass> {

	private LineRecordReader lineRecordReader = null;
	private Text key = null;
	private FirstClass value = null;
	
	@Override
	public void close() throws IOException {
		if(null != lineRecordReader){
			lineRecordReader.close();
			lineRecordReader = null;
		}
		key = null;
		value = null;
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		return key;
	}

	@Override
	public FirstClass getCurrentValue() throws IOException,
			InterruptedException {
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return lineRecordReader.getProgress();
	}

	@Override
	public void initialize(InputSplit arg0, TaskAttemptContext arg1)
			throws IOException, InterruptedException {
		close();
		lineRecordReader = new LineRecordReader();
		lineRecordReader.initialize(arg0, arg1);
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if(!lineRecordReader.nextKeyValue()){
			key = null;
			value = null;
			return false;
		}
		
		Text val = lineRecordReader.getCurrentValue();
		String line = val.toString();
		String[] arr = line.split("t");
		key = new Text(arr[0]);
		value = new FirstClass(arr[1].trim());
		
		return true;
	}

}
