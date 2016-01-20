package hadoop2_6.hdfs;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungang on 2016/1/11.15:30
 */
public class TextFilesExample {


    public static List<String> readLines(Path location, Configuration conf) throws Exception {
        FileSystem fileSystem = FileSystem.get(location.toUri(), conf);
        CompressionCodecFactory factory = new CompressionCodecFactory(conf);
        FileStatus[] items = fileSystem.listStatus(location);
        if (null == items)
            return new ArrayList<>();

        List<String> results = Lists.newArrayList();
        for (FileStatus item : items){

            //ignoring files like _SUCCESS
            if (item.getPath().getName().startsWith("_")){
                continue;
            }

            CompressionCodec codec = factory.getCodec(item.getPath());
            InputStream stream = null;

            // check if we have a compression codec we need to use
            if (codec != null) {
                stream = codec.createInputStream(fileSystem.open(item.getPath()));
            }else {
                stream = fileSystem.open(item.getPath());
            }

            StringWriter writer = new StringWriter();
            IOUtils.copy(stream,writer,"UTF-8");
            String raw = writer.toString();
            String[] resulting = raw.split("\n");
            for (String res : resulting){
                results.add(res);
            }
        }
        return  results;
    }


    public static void main(String[] args) throws Exception{
        // example usage:
        Path myfile = new Path("hdfs:192.168.3.141:9000/tmp/hive_test/1209");
        List<String> results = readLines(myfile, new Configuration());
        for (String res : results){
            System.out.println("Line Data : " + res);
        }
    }
}
