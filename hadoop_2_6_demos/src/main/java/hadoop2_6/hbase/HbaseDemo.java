package hadoop2_6.hbase;//package com.sung.hadoop2_6.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
* Created by sungang on 2015/11/12.13:44
*/
public class HbaseDemo {

    private Configuration conf = null;

    public void init(){
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop01,hadoop02,hadoop03");
    }

    public void testDrop() throws Exception{
        HBaseAdmin admin = new HBaseAdmin(conf);
        admin.disableTable("account");
        admin.deleteTable("account");
        admin.close();
    }

    public void testPut() throws Exception{
        HTable table = new HTable(conf, "user");
        Put put = new Put(Bytes.toBytes("rk0003"));
        put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("liuyan"));
        table.put(put);
        table.close();
    }

    public void testGet() throws Exception{
        //HTablePool pool = new HTablePool(conf, 10);
        //HTable table = (HTable) pool.getTable("user");
        HTable table = new HTable(conf, "user");
        Get get = new Get(Bytes.toBytes("rk0001"));
        //get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
        get.setMaxVersions(5);
        Result result = table.get(get);
        //result.getValue(family, qualifier)
        for(KeyValue kv : result.list()){
            String family = new String(kv.getFamily());
            System.out.println(family);
            String qualifier = new String(kv.getQualifier());
            System.out.println(qualifier);
            System.out.println(new String(kv.getValue()));
        }
        table.close();
    }

    public void testScan() throws Exception{
        HTablePool pool = new HTablePool(conf, 10);
        HTableInterface table = pool.getTable("user");
        Scan scan = new Scan(Bytes.toBytes("rk0001"), Bytes.toBytes("rk0002"));
        scan.addFamily(Bytes.toBytes("info"));
        ResultScanner scanner = table.getScanner(scan);
        for(Result r : scanner){
            /**
             for(KeyValue kv : r.list()){
             String family = new String(kv.getFamily());
             System.out.println(family);
             String qualifier = new String(kv.getQualifier());
             System.out.println(qualifier);
             System.out.println(new String(kv.getValue()));
             }
             */
            byte[] value = r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"));
            System.out.println(new String(value));
        }
        pool.close();
    }


    public void testDel() throws Exception{
        HTable table = new HTable(conf, "user");
        Delete del = new Delete(Bytes.toBytes("rk0001"));
        del.deleteColumn(Bytes.toBytes("data"), Bytes.toBytes("pic"));
        table.delete(del);
        table.close();
    }




    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop01,hadoop02,hadoop03");
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor td = new HTableDescriptor("account");
        HColumnDescriptor cd = new HColumnDescriptor("info");
        cd.setMaxVersions(10);
        td.addFamily(cd);
        admin.createTable(td);
        admin.close();

    }

    public void createTable(String tableName, int maxVersion, String... cf){

    }
}
