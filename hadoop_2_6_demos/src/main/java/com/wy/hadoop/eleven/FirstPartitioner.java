package com.wy.hadoop.eleven;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FirstPartitioner extends Partitioner<IntPair, Text> {

	@Override
	public int getPartition(IntPair key, Text value, int numPartition) {
		return Math.abs(key.hashCode()*127)%numPartition;
	}

}
