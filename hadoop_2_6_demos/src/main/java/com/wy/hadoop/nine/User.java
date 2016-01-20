package com.wy.hadoop.nine;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class User implements WritableComparable<User> {

	private String userNo="";
	private String userName="";
	private String cityNo = "";
	private String cityName = "";
	//0:city 1:user
	private int flag = 0;
	
	public User(){}
	public User(String userNo,String userName,String cityNo,String cityName,int flag){
		this.userNo = userNo;
		this.userName = userName;
		this.cityNo = cityNo;
		this.cityName = cityName;
		this.flag = flag;
	}
	public User(User user){
		this.userNo = user.getUserNo();
		this.userName = user.getUserName();
		this.cityNo = user.getCityNo();
		this.cityName = user.getCityName();
		this.flag = user.getFlag();
	}
	
	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(userNo);
		output.writeUTF(userName);
		output.writeUTF(cityNo);
		output.writeUTF(cityName);
		output.writeInt(flag);
		
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.userNo = input.readUTF();
		this.userName = input.readUTF();
		this.cityNo = input.readUTF();
		this.cityName = input.readUTF();
		this.flag = input.readInt();
		
	}

	@Override
	public int compareTo(User o) {
		return 0;
	}
	
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return userNo+"  "+userName+"  "+cityName;
	}

	
	
}
