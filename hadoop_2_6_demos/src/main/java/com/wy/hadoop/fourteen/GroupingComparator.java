package com.wy.hadoop.fourteen;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupingComparator extends WritableComparator {

	public GroupingComparator(){
		super(IntPair.class,true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		IntPair o1 = (IntPair)a;
		IntPair o2 = (IntPair)b;
		return o1.getFirstKey().compareTo(o2.getFirstKey());
	}
	
	
}
