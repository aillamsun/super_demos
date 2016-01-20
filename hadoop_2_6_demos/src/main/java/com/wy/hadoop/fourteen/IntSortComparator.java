package com.wy.hadoop.fourteen;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class IntSortComparator extends WritableComparator {

	protected IntSortComparator() {
		super(IntPair.class,true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		IntPair o1 = (IntPair)a;
		IntPair o2 = (IntPair)b;
		if(!o1.getFirstKey().equals(o2.getFirstKey())){
			return o1.getFirstKey().compareTo(o2.getFirstKey());
		}else{
			return o1.getSecondKey()-o2.getSecondKey();
		}
	}

}
