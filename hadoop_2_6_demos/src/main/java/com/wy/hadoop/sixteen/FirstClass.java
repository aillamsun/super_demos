package com.wy.hadoop.sixteen;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class FirstClass implements Writable {
	private String value;
	
	public FirstClass(){}
	public FirstClass(String value){
		this.value = value;
	}
	
	@Override
	public void readFields(DataInput input) throws IOException {
		this.value = input.readUTF().trim();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(value);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "FirstClasst"+value;
	}

	
	
	
}
