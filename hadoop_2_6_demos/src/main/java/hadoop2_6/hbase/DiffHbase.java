package hadoop2_6.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sungang on 2016/1/11.16:43
 */
public class DiffHbase {
    public static Configuration configuration = null;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "idc01-hd-ds-01,idc01-hd-ds-02,idc01-hd-ds-03");
    }

    public static void selectRowKey(String tablename, String rowKey) throws IOException {
        HTable table = new HTable(configuration, tablename);
        Get g = new Get(rowKey.getBytes());
        Result rs = table.get(g);

        for (KeyValue kv : rs.raw()) {
            System.out.println("--------------------" + new String(kv.getRow()) + "----------------------------");
            System.out.println("Column Family: " + new String(kv.getFamily()));
            System.out.println("Column       :" + new String(kv.getQualifier()) + "t");
            System.out.println("value        : " + new String(kv.getValue()));
        }
    }

    public static void selectRowKeyFamily(String tablename, String rowKey, String family) throws IOException {
        HTable table = new HTable(configuration, tablename);
        Get g = new Get(rowKey.getBytes());
        g.addFamily(Bytes.toBytes(family));
        Result rs = table.get(g);
        for (KeyValue kv : rs.raw()) {
            System.out.println("--------------------" + new String(kv.getRow()) + "----------------------------");
            System.out.println("Column Family: " + new String(kv.getFamily()));
            System.out.println("Column       :" + new String(kv.getQualifier()) + "t");
            System.out.println("value        : " + new String(kv.getValue()));
        }
    }

    public static void selectRowKeyFamilyColumn(String tablename, String rowKey, String family, String column)
            throws IOException {
        HTable table = new HTable(configuration, tablename);
        Get g = new Get(rowKey.getBytes());
        g.addColumn(family.getBytes(), column.getBytes());

        Result rs = table.get(g);

        for (KeyValue kv : rs.raw()) {
            System.out.println("--------------------" + new String(kv.getRow()) + "----------------------------");
            System.out.println("Column Family: " + new String(kv.getFamily()));
            System.out.println("Column       :" + new String(kv.getQualifier()) + "t");
            System.out.println("value        : " + new String(kv.getValue()));
        }
    }


    private static final String USAGE = "Usage: DiffHbase [-o outfile] tablename infile filterColumns...";

    /**
     * Prints the usage message and exists the program.
     *
     * @param message The message to print first.
     */
    private static void printUsage(String message) {
        System.err.println(message);
        System.err.println(USAGE);
        throw new RuntimeException(USAGE);
    }

    private static void PrintId(String id, Result rs) {
        String value = Bytes.toString(rs.getValue(ColumnUtils.getFamily("info:url"), ColumnUtils.getQualifier("info:url")));
        if (value == null) {
            System.out.println(id + "\tNULL");
        } else {
            System.out.println(id + "\t" + value);
        }
    }

    private static void WriteId(String id, Result rs, FileOutputStream os) {
        String value = Bytes.toString(rs.getValue(ColumnUtils.getFamily("info:url"), ColumnUtils.getQualifier("info:url")));
        try {
            if (value == null) {
                os.write((id + "\tNULL\n").getBytes());
            } else {
                os.write((id + "\t" + value + "\n").getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void PrintRow(String id, Result rs) {

        System.out.println("--------------------" + id + "----------------------------");
        for (KeyValue kv : rs.raw()) {
            System.out.println(new String(kv.getFamily()) + ":" + new String(kv.getQualifier()) + " : " + new String(kv.getValue()));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            printUsage("Too few arguments");
        }

        String outfile = null;
        String tablename = args[0];
        String dictfile = args[1];
        int skilLen = 2;

        if (args[0].equals("-o")) {
            outfile = args[1];
            tablename = args[2];
            dictfile = args[3];
            skilLen = 4;
        }

        HTable table = new HTable(configuration, tablename);

        String[] filterColumns = new String[args.length - skilLen];
        System.arraycopy(args, skilLen, filterColumns, 0, args.length - skilLen);

        System.out.println("filterColumns: ");
        for (int i = 0; i < filterColumns.length; ++i) {
            System.out.println("\t" + filterColumns[i]);
        }

        FileOutputStream os = null;
        if (outfile != null) {
            os = new FileOutputStream(outfile);
        }

        int count = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//??????

        File srcFile = new File(dictfile);
        FileInputStream in = new FileInputStream(srcFile);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        String read = null;
        while ((read = br.readLine()) != null) {
            String[] split = read.trim().split("\\s");   // space split
            if (split.length < 1) {
                System.out.println("Error line: " + read);
                continue;
            }

            if (++count % 1000 == 0) {
                System.out.println(df.format(new Date()) + " : " + count + " rows processed.");  // new Date()?????????
            }
            // System.out.println("ROWKEY:" + split[0]);

            Get g = new Get(split[0].getBytes());
            Result rs = table.get(g);
            if (rs == null) {
                System.out.println("No Result for " + split[0]);
                continue;
            }

            for (int i = 0; i < filterColumns.length; ++i) {
                String value = Bytes.toString(rs.getValue(ColumnUtils.getFamily(filterColumns[i]), ColumnUtils.getQualifier(filterColumns[i])));
                if (value == null) {
                    if (os == null) {
                        PrintId(split[0], rs);
                    } else {
                        WriteId(split[0], rs, os);
                    }

                    // PrintRow(split[0], rs);
                    break;
                }
            }
        }

        br.close();
        isr.close();
        in.close();

    }
}
