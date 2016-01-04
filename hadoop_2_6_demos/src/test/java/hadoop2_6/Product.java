package hadoop2_6;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Product {

	public static void main(String[] args) {
		System.out.println("======================Start=============================");
		long start = System.currentTimeMillis();
		writeToTxt();
		System.out.println("=======End=========total time =========" + (start-(System.currentTimeMillis())));
	}
	
	public static void writeToTxt() {
		Set<DataBean> set = new HashSet<DataBean>();
		for (int i = 1; i <= 1000000; i++) {
			String endNum = String.valueOf(i);
			String phoneNo = "";
			if (endNum.endsWith("0")) {
				phoneNo = "13076061650";
			}else if (endNum.endsWith("1")) {
				phoneNo = "13076061651";
			}else if (endNum.endsWith("2")) {
				phoneNo = "13076061652";
			}else if (endNum.endsWith("3")) {
				phoneNo = "13076061653";
			}else if (endNum.endsWith("4")) {
				phoneNo = "13076061654";
			}else if (endNum.endsWith("5")) {
				phoneNo = "13076061655";
			}else if (endNum.endsWith("6")) {
				phoneNo = "13076061656";
			}else if (endNum.endsWith("7")) {
				phoneNo = "13076061657";
			}else if (endNum.endsWith("8")) {
				phoneNo = "13076061658";
			}else if (endNum.endsWith("9")) {
				phoneNo = "13076061659";
			}
			String upPayLoad = RandomStringUtils.random(4, "123456789");
			String downPayLoad = RandomStringUtils.random(4, "123456789");
			DataBean dataBean = new DataBean(phoneNo, Long.parseLong(upPayLoad),Long.parseLong(downPayLoad));
			set.add(dataBean);
		}
		Iterator iterator = set.iterator();
		File file = new File("E:\\dataCount.txt");
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			while (iterator.hasNext()) {
				writer.write(iterator.next().toString());
				writer.newLine();// 换行
			}
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static class DataBean{
		private String phoneNo;
		private Long upPayLoad;
		private Long dowmPayLoad;
		
		
		public DataBean() {
		}
		public DataBean(String phoneNo, Long upPayLoad, Long dowmPayLoad) {
			this.phoneNo = phoneNo;
			this.upPayLoad = upPayLoad;
			this.dowmPayLoad = dowmPayLoad;
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
		public Long getDowmPayLoad() {
			return dowmPayLoad;
		}
		public void setDowmPayLoad(Long dowmPayLoad) {
			this.dowmPayLoad = dowmPayLoad;
		}
		@Override
		public String toString() {
			return this.phoneNo+"\t"+this.upPayLoad+"\t"+this.dowmPayLoad;
		}
	}
}
