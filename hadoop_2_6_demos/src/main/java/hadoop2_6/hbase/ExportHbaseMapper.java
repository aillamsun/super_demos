package hadoop2_6.hbase;


import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungang on 2016/1/11.16:47
 */
public class ExportHbaseMapper extends TableMapper<Text, Text> implements Configurable {

    private static final Text keyTEXT = new Text();
    private static final Text SENDTEXT = new Text();

    private Configuration conf = null;

    private long startTime = 0;
    List<String> columnMap = null;

    private long rCount = 0;
    private long errCount = 0;
    private int debug = 0;
    private long thresCount = 10000;

    public void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

        rCount++;

        String itemid = Bytes.toString(key.get());
        if (itemid.contains("&")) {
            context.getCounter("Error", "rowkey contains \"&\"").increment(1);
            return;
        }

        StringBuffer outstr = new StringBuffer();
        for (String col : columnMap) {

            String tmp = Bytes.toString(value.getValue(ColumnUtils.getFamily(col), ColumnUtils.getQualifier(col)));
            if (tmp == null) {
                context.getCounter("Error", col + " No value in hbase").increment(1);

                errCount++;
                if (debug > 0 && (errCount % thresCount == 0)) {
                    System.err.println(itemid + ": doesn't has " + col + " data!");
                }

                outstr.append("NULL" + "\t");
            } else {
                if (tmp.contains("guangtaobao")) {
                    outstr.append("1" + "\t");
                } else {
                    outstr.append(tmp.trim() + "\t");
                }
            }
        }

        if (!outstr.toString().isEmpty()) {

            SENDTEXT.set(outstr.toString());
            keyTEXT.set(itemid);
            context.write(keyTEXT, SENDTEXT);

            if (debug > 0 && (rCount % thresCount * 10000 == 0)) {
                System.out.println(SENDTEXT.toString() + keyTEXT.toString());
            }
        } else {
            context.getCounter("Error", "No Colume output").increment(1);
            return;
        }
    }

    /**
     * Returns the current configuration.
     *
     * @return The current configuration.
     * @see org.apache.hadoop.conf.Configurable#getConf()
     */
    @Override
    public Configuration getConf() {
        return conf;
    }

    /**
     * Sets the configuration. This is used to set up the index configuration.
     *
     * @param configuration The configuration to set.
     * @see org.apache.hadoop.conf.Configurable#setConf(org.apache.hadoop.conf.Configuration)
     */
    @Override
    public void setConf(Configuration configuration) {
        this.conf = configuration;

        startTime = Long.parseLong(conf.get("hbase.index.startTime"));
        thresCount = Long.parseLong(conf.get("hbase.index.debugThreshold"));
        debug = Integer.parseInt(conf.get("hbase.index.debug"));

        String[] columns = conf.get("hbase.index.column").split("\\|");

        columnMap = new ArrayList<String>();
        for (String column : columns) {
            System.out.println("Output column: " + column);

            columnMap.add(column);
        }

    }
}
