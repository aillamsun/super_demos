package com.wy.hadoop.fourteen;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class IntPair implements WritableComparable<IntPair> {
	
	private String firstKey;
	private int secondKey;
	
	public IntPair(){};
	public IntPair(String one,int two){
		this.firstKey = one;
		this.secondKey = two;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.firstKey = input.readUTF();
		this.secondKey = input.readInt();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(firstKey);
		output.writeInt(secondKey);
	}

	@Override
	public int compareTo(IntPair o) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getFirstKey() {
		return firstKey;
	}
	public void setFirstKey(String firstKey) {
		this.firstKey = firstKey;
	}
	public int getSecondKey() {
		return secondKey;
	}
	public void setSecondKey(int secondKey) {
		this.secondKey = secondKey;
	}

}
