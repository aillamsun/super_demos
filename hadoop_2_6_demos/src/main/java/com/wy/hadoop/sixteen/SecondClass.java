package com.wy.hadoop.sixteen;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class SecondClass implements Writable {

	private String userName;
	private int classNum;
	
	public SecondClass(){}
	public SecondClass(String username,int classnum){
		this.userName = username;
		this.classNum = classnum;
	}
	
	@Override
	public void readFields(DataInput input) throws IOException {
		this.userName = input.readUTF().trim();
		this.classNum  = input.readInt();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(userName);
		output.writeInt(classNum);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	@Override
	public String toString() {
		return "SecondClasst"+userName+"t"+classNum;
	}

}
