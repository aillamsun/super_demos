package hadoop2_6.mr.log_analytics_mapreduce.custom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * custom hadoop file format for HTTP log files, provides a generic splitting mechanism for HDFS file
 */
public class HTTPLogInputFormat extends FileInputFormat<LongWritable, LogWritable> {

  @Override
  public RecordReader<LongWritable, LogWritable> createRecordReader(
      InputSplit arg0, TaskAttemptContext arg1) throws IOException,
      InterruptedException {
    return new HTTPLogRecordReader();
  }

}