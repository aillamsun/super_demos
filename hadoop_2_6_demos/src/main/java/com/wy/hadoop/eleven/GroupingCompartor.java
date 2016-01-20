package com.wy.hadoop.eleven;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupingCompartor extends WritableComparator {

	protected GroupingCompartor() {
		super(IntPair.class,true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		IntPair a1=(IntPair)a;
		IntPair b1=(IntPair)b;
		
		return a1.getFirst().compareTo(b1.getFirst());
	}
	
	

}
