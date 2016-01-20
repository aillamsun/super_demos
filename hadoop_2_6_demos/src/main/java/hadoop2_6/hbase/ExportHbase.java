package hadoop2_6.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungang on 2016/1/11.16:46
 */
public class ExportHbase {

    private static final String INFOCATEGORY = "info:storecategory";

    private static final String USAGE = "Usage: ExportHbase " +
            "-r <numReduceTasks> -indexConf <iconfFile>\n" +
            "-indexDir <indexDir> -webSite <amazon> [-needupdate <true> -isVisible -startTime <long>] -table <tableName> -columns <columnName1> " +
            "[<columnName2> ...]";

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

    /**
     * Creates a new job.
     *
     * @param conf
     * @param args The command line arguments.
     * @throws IOException When reading the configuration fails.
     */
    public static Job createSubmittableJob(Configuration conf, String[] args)
            throws IOException {
        if (args.length < 7) {
            printUsage("Too few arguments");
        }

        int numReduceTasks = 1;
        String iconfFile = null;
        String indexDir = null;
        String tableName = null;
        String website = null;
        String needupdate = "";
        String expectShopGrade = "";
        String dino = "6";
        String isdebug = "0";
        long debugThreshold = 10000;
        String debugThresholdStr = Long.toString(debugThreshold);
        String queue = "offline";

        long endTime = Long.MAX_VALUE;
        int maxversions = 1;
        long startTime = System.currentTimeMillis() - 28 * 24 * 60 * 60 * 1000l;
        long distartTime = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000l;
        long diusedTime = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000l;
        String startTimeStr = Long.toString(startTime);
        String diusedTimeStr = Long.toString(diusedTime);
        String quorum = null;

        String isVisible = "";
        List<String> columns = new ArrayList<String>();

        boolean bFilter = false;

        // parse args
        for (int i = 0; i < args.length - 1; i++) {
            if ("-r".equals(args[i])) {
                numReduceTasks = Integer.parseInt(args[++i]);
            } else if ("-indexConf".equals(args[i])) {
                iconfFile = args[++i];
            } else if ("-indexDir".equals(args[i])) {
                indexDir = args[++i];
            } else if ("-table".equals(args[i])) {
                tableName = args[++i];
            } else if ("-webSite".equals(args[i])) {
                website = args[++i];
            } else if ("-startTime".equals(args[i])) {
                startTimeStr = args[++i];
                startTime = Long.parseLong(startTimeStr);
            } else if ("-needupdate".equals(args[i])) {
                needupdate = args[++i];
            } else if ("-isVisible".equals(args[i])) {
                isVisible = "true";
            } else if ("-shopgrade".equals(args[i])) {
                expectShopGrade = args[++i];
            } else if ("-queue".equals(args[i])) {
                queue = args[++i];
            } else if ("-dino".equals(args[i])) {
                dino = args[++i];
            } else if ("-maxversions".equals(args[i])) {
                maxversions = Integer.parseInt(args[++i]);
            } else if ("-distartTime".equals(args[i])) {
                distartTime = Long.parseLong(args[++i]);
            } else if ("-diendTime".equals(args[i])) {
                endTime = Long.parseLong(args[++i]);
            } else if ("-diusedTime".equals(args[i])) {
                diusedTimeStr = args[++i];
                diusedTime = Long.parseLong(diusedTimeStr);
            } else if ("-quorum".equals(args[i])) {
                quorum = args[++i];
            } else if ("-filter".equals(args[i])) {
                bFilter = true;
            } else if ("-columns".equals(args[i])) {
                columns.add(args[++i]);
                while (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    String columnname = args[++i];
                    columns.add(columnname);
                    System.out.println("args column----: " + columnname);
                }
            } else if ("-debugThreshold".equals(args[i])) {
                isdebug = "1";
                debugThresholdStr = args[++i];
                debugThreshold = Long.parseLong(debugThresholdStr);
            } else {
                printUsage("Unsupported option " + args[i]);
            }
        }

        if (distartTime > endTime) {
            printUsage("distartTime must <= diendTime");
        }

        if (indexDir == null || tableName == null || columns.isEmpty()) {
            printUsage("Index directory, table name and at least one column must " +
                    "be specified");
        }

        if (iconfFile != null) {
            // set index configuration content from a file
            String content = readContent(iconfFile);
            conf.set("hbase.index.conf", content);
            conf.set("hbase.website.name", website);
            conf.set("hbase.needupdate.productDB", needupdate);
            conf.set("hbase.expect.shopgrade", expectShopGrade);
            conf.set("hbase.di.no", dino);
            conf.set("hbase.expect.item.visible", isVisible);
            conf.set("hbase.index.startTime", startTimeStr);
            conf.set("hbase.index.diusedTime", diusedTimeStr);
            conf.set("hbase.index.debugThreshold", debugThresholdStr);
            conf.set("hbase.index.debug", isdebug);
            if (quorum != null) {
                conf.set("hbase.zookeeper.quorum", quorum);
            }
            String temp = "";
            for (String column : columns) {
                temp = temp + column + "|";
            }
            temp = temp.substring(0, temp.length() - 1);
            conf.set("hbase.index.column", temp);
            System.out.println("hbase.index.column: " + temp);
        }


        Job job = new Job(conf, "export data from table " + tableName);
        ((JobConf) job.getConfiguration()).setQueueName(queue);

        // number of indexes to partition into
        job.setNumReduceTasks(numReduceTasks);
        Scan scan = new Scan();
        scan.setCacheBlocks(false);

        // limit scan range
        scan.setTimeRange(distartTime, endTime);
        //  scan.setMaxVersions(maxversions);
        scan.setMaxVersions(1);

        /* limit scan columns */
        for (String column : columns) {
            scan.addColumn(ColumnUtils.getFamily(column), ColumnUtils.getQualifier(column));
            scan.addFamily(ColumnUtils.getFamily(column));
        }

        // set filter
        if (bFilter) {
            System.out.println("only export guangtaobao data. ");
            SingleColumnValueFilter filter = new SingleColumnValueFilter(
                    Bytes.toBytes("info"),
                    Bytes.toBytes("producttype"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("guangtaobao")));
            filter.setFilterIfMissing(true);
            scan.setFilter(filter);
        }

        TableMapReduceUtil.initTableMapperJob(tableName, scan, ExportHbaseMapper.class, Text.class, Text.class, job);
        // job.setReducerClass(ExportHbaseReducer.class);
        FileOutputFormat.setOutputPath(job, new Path(indexDir));


        return job;
    }

    /**
     * Reads xml file of indexing configurations.  The xml format is similar to
     * hbase-default.xml and hadoop-default.xml. For an example configuration,
     * see the <code>createIndexConfContent</code> method in TestTableIndex.
     *
     * @param fileName The file to read.
     * @return XML configuration read from file.
     * @throws IOException When the XML is broken.
     */
    private static String readContent(String fileName) throws IOException {
        File file = new File(fileName);
        int length = (int) file.length();
        if (length == 0) {
            printUsage("Index configuration file " + fileName + " does not exist");
        }

        int bytesRead = 0;
        byte[] bytes = new byte[length];
        FileInputStream fis = new FileInputStream(file);

        try {
            // read entire file into content
            while (bytesRead < length) {
                int read = fis.read(bytes, bytesRead, length - bytesRead);
                if (read > 0) {
                    bytesRead += read;
                } else {
                    break;
                }
            }
        } finally {
            fis.close();
        }

        return new String(bytes, 0, bytesRead, HConstants.UTF8_ENCODING);
    }

    /**
     * The main entry point.
     *
     * @param args The command line arguments.
     * @throws Exception When running the job fails.
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        Job job = createSubmittableJob(conf, otherArgs);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
