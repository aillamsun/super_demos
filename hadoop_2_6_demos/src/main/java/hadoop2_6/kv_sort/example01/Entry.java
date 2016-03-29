package hadoop2_6.kv_sort.example01;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by sungang on 2016/3/28.11:24
 *
 * 将旧的Key（natural key）和Value组合成新的Key（composite key）
 */
public class Entry implements WritableComparable<Entry>{

    private String yearMonth;
    private int count;

    @Override
    public int compareTo(Entry entry) {
        int result = this.yearMonth.compareTo(entry.getYearMonth());
        if (result == 0){
            result = compare(count,entry.getCount());
        }
        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(yearMonth);
        dataOutput.writeInt(count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.yearMonth = dataInput.readUTF();
        this.count = dataInput.readInt();
    }

    public static int compare(int a, int b) {
        return a < b ? -1 : (a > b ? 1 : 0);
    }


    @Override
    public String toString() {
        return yearMonth;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
