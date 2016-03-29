package hadoop2_6.kv_sort.example01;

import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by sungang on 2016/3/28.11:29
 * 使得natural key相同的数据分派到同一个Reduce中
 */
public class EntryPartitioner extends Partitioner<Entry,Integer>{

    @Override
    public int getPartition(Entry entry, Integer integer, int numberPartitions) {
        return Math.abs(entry.getYearMonth().hashCode() % numberPartitions);
    }
}
