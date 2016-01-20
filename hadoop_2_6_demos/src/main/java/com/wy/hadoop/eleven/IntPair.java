package com.wy.hadoop.eleven;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class IntPair implements WritableComparable<IntPair> {
	
	private String first;
	private String second;
	
	public IntPair(){};
	public IntPair(String one,String two){
		this.first = one;
		this.second = two;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.first = input.readUTF();
		this.second = input.readUTF();
		
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(first);
		output.writeUTF(second);
		
	}

	@Override
	public int compareTo(IntPair o) {
		if(!this.first.equals(o.getFirst())){
			return this.first.compareTo(o.getFirst());
		}else{
			if(!this.second.equals(o.getSecond())){
				return this.second.compareTo(o.getSecond());
			}else{
				return 0;
			}
		}
	}
	
	
	
	@Override
	public int hashCode() {
		return this.first.hashCode()*133+this.second.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}else if(this == obj){
			return true;
		}else if(obj instanceof IntPair){
			IntPair o = (IntPair)obj;
			return this.first.equals(o.getFirst()) && this.second.equals(o.getSecond());
		}else{
			return false;
		}
	}
	
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	
	

}
