package com.wy.hadoop.fifteen;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class FlagStringDataType implements WritableComparable<FlagStringDataType> {
	private String value;
	private int flag;//0:phone,1:user
	
	public FlagStringDataType(){}
	public FlagStringDataType(String value,int flag){
		this.value = value;
		this.flag = flag;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.value = input.readUTF();
		this.flag = input.readInt();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(value);
		output.writeInt(flag);
		
	}

	@Override
	public int compareTo(FlagStringDataType o) {
		if(this.flag>=o.getFlag()){
			if(this.flag>o.getFlag()){
				return 1;
			}
		}else{
			return -1;
		}
		return this.value.compareTo(o.getValue());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	
}
