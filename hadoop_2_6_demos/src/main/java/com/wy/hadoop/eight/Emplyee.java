package com.wy.hadoop.eight;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Emplyee implements WritableComparable<Emplyee> {

	//empno/ename/deptno/dname/flag
	private String empno="";
	private String ename="";
	private String deptno="";
	private String dname="";
	private int flag =0;//0:部门 1：员工
	
	public Emplyee(){
		
	}
	
	public Emplyee(String empno,String ename,String deptno,String dname,int flag){
		this.empno = empno;
		this.ename = ename;
		this.deptno = deptno;
		this.dname = dname;
		this.flag = flag;
	}
	
	public Emplyee(Emplyee emplyee){
		this.empno = emplyee.getEmpno();
		this.ename = emplyee.getEname();
		this.deptno = emplyee.getDeptno();
		this.dname = emplyee.getDname();
		this.flag = emplyee.getFlag();
	}
	
	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(empno);
		output.writeUTF(ename);
		output.writeUTF(deptno);
		output.writeUTF(dname);
		output.writeInt(flag);
		
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.empno = input.readUTF();
		this.ename = input.readUTF();
		this.deptno = input.readUTF();
		this.dname = input.readUTF();
		this.flag = input.readInt();
		
	}

	@Override
	public int compareTo(Emplyee o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		
		return this.empno+"  "+this.ename+"  "+this.dname+"  "+this.deptno;
	}

	
	
	
}
