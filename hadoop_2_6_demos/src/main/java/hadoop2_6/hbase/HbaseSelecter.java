package hadoop2_6.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * Created by sungang on 2016/1/11.16:45
 */
public class HbaseSelecter {
    public static Configuration configuration = null;

    static {
        configuration = HBaseConfiguration.create();
        //configuration.set("hbase.master", "192.168.0.201:60000");
        configuration.set("hbase.zookeeper.quorum", "idc01-hd-nd-03,idc01-hd-nd-04,idc01-hd-nd-05");
        //configuration.set("hbase.zookeeper.property.clientPort", "2181");
    }

    public static void selectRowKey(String tablename, String rowKey) throws IOException {
        HTable table = new HTable(configuration, tablename);
        Get g = new Get(rowKey.getBytes());
        Result rs = table.get(g);

        for (KeyValue kv : rs.raw()) {
            System.out.println("--------------------" + new String(kv.getRow()) + "----------------------------");
            System.out.println("Column Family: " + new String(kv.getFamily()));
            System.out.println("Column       :" + new String(kv.getQualifier()));
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
            System.out.println("Column       :" + new String(kv.getQualifier()));
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
            System.out.println("Column       :" + new String(kv.getQualifier()));
            System.out.println("value        : " + new String(kv.getValue()));
        }
    }

    public static void selectFilter(String tablename, List<String> arr) throws IOException {
        HTable table = new HTable(configuration, tablename);
        Scan scan = new Scan();// ????????
        FilterList filterList = new FilterList(); // ???List

        for (String v : arr) { // ??0????1????3???
            String[] wheres = v.split(",");

            filterList.addFilter(new SingleColumnValueFilter(// ???
                    wheres[0].getBytes(), wheres[1].getBytes(),

                    CompareOp.EQUAL,// ???????" and "???
                    wheres[2].getBytes()));
        }
        scan.setFilter(filterList);
        ResultScanner ResultScannerFilterList = table.getScanner(scan);
        for (Result rs = ResultScannerFilterList.next(); rs != null; rs = ResultScannerFilterList.next()) {
            for (KeyValue kv : rs.list()) {
                System.out.println("--------------------" + new String(kv.getRow()) + "----------------------------");
                System.out.println("Column Family: " + new String(kv.getFamily()));
                System.out.println("Column       :" + new String(kv.getQualifier()));
                System.out.println("value        : " + new String(kv.getValue()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: HbaseSelecter table key");
            System.exit(-1);
        }

        System.out.println("Table: " + args[0] + " , key: " + args[1]);
        selectRowKey(args[0], args[1]);

        /*
        System.out.println("------------------------??  ??----------------------------------");
        selectRowKey("b2c", "yihaodian1002865");
        selectRowKey("b2c", "yihaodian1003396");

        System.out.println("------------------------??+?? ??----------------------------------");
        selectRowKeyFamily("riapguh", "??A", "user");
        selectRowKeyFamily("riapguh", "??B", "user");

        System.out.println("------------------------??+??+?? ??----------------------------------");
        selectRowKeyFamilyColumn("riapguh", "??A", "user", "user_code");
        selectRowKeyFamilyColumn("riapguh", "??B", "user", "user_code");

        System.out.println("------------------------?? ??----------------------------------");
        List<String> arr = new ArrayList<String>();
        arr.add("dpt,dpt_code,d_001");
        arr.add("user,user_code,u_0001");
        selectFilter("riapguh", arr);
        */
    }
}
