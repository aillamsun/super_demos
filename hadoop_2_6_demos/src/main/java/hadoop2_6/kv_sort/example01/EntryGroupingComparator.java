package hadoop2_6.kv_sort.example01;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Created by sungang on 2016/3/28.11:31
 *
 */
public class EntryGroupingComparator extends WritableComparator{


    public EntryGroupingComparator() {
        super(Entry.class, true);
    }


    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Entry a1 = (Entry)a;
        Entry b1 = (Entry)b;
        return a1.getYearMonth().compareTo(b1.getYearMonth());
    }
}
