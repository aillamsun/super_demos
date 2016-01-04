package hadoop2_6.day3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class DataBean implements Writable{
	
	private String phoneNo;
	private Long upPayLoad;
	private Long downPayLoad;
	private Long totalPayLoad;
	
	public DataBean() {
	}
	public DataBean(String phoneNo, Long upPayLoad, Long downPayLoad) {
		this.phoneNo = phoneNo;
		this.upPayLoad = upPayLoad;
		this.downPayLoad = downPayLoad;
		this.totalPayLoad = (upPayLoad + downPayLoad);
	}
	/**
	 * 序列化 
	 * 注意: 1 ：顺序 ,2 ：类型 
	 * 保持一致
	 */
	public void write(DataOutput out) throws IOException {
		out.writeUTF(phoneNo);
		out.writeLong(upPayLoad);
		out.writeLong(downPayLoad);
		out.writeLong(totalPayLoad);
	}
	/**
	 * 反序列化
	 */
	public void readFields(DataInput in) throws IOException {
		this.phoneNo = in.readUTF();
		this.upPayLoad = in.readLong();
		this.downPayLoad = in.readLong();
		this.totalPayLoad = in.readLong();
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Long getUpPayLoad() {
		return upPayLoad;
	}

	public void setUpPayLoad(Long upPayLoad) {
		this.upPayLoad = upPayLoad;
	}

	public Long getDownPayLoad() {
		return downPayLoad;
	}

	public void setDownPayLoad(Long downPayLoad) {
		this.downPayLoad = downPayLoad;
	}

	public Long getTotalPayLoad() {
		return totalPayLoad;
	}

	public void setTotalPayLoad(Long totalPayLoad) {
		this.totalPayLoad = totalPayLoad;
	}
	@Override
	public String toString() {
		return this.upPayLoad + "\t" + this.downPayLoad + "\t" + this.totalPayLoad;
	}
}
