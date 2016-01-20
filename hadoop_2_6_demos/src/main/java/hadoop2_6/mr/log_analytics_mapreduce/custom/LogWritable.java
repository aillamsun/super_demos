package hadoop2_6.mr.log_analytics_mapreduce.custom;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * custom hadoop key format for http log events
 */
public class LogWritable implements WritableComparable<LogWritable> {

  private Text userIP, timestamp, requestType, requestPage, browser;
  private IntWritable responseSize, status;

  public LogWritable() {
    this.userIP = new Text();
    this.timestamp =  new Text();
    this.requestType = new Text();
    this.requestPage = new Text();
    this.browser = new Text();
    this.responseSize = new IntWritable();
    this.status = new IntWritable();
  }

  public void set (String userIP, String timestamp, String requestType, String requestPage, String browser,
                   int bytes, int status)
  {
    this.userIP.set(userIP);
    this.timestamp.set(timestamp);
    this.requestType.set(requestType);
    this.requestPage.set(requestPage);
    this.browser.set(browser);
    this.responseSize.set(bytes);
    this.status.set(status);
  }

  /*
   * de-serialize the input data and populate the fields of the Writable object
   */
  @Override
  public void readFields(DataInput in) throws IOException {
    userIP.readFields(in);
    timestamp.readFields(in);
    requestType.readFields(in);
    requestPage.readFields(in);
    browser.readFields(in);
    responseSize.readFields(in);
    status.readFields(in);
  }

  /*
   * write the fields of the Writable object to the underlying stream
   */
  @Override
  public void write(DataOutput out) throws IOException {
    userIP.write(out);
    timestamp.write(out);
    requestType.write(out);
    requestPage.write(out);
    browser.write(out);
    responseSize.write(out);
    status.write(out);
  }

  /*
   * required for WritableComparable to make comparison between keys
   */
  @Override
  public int compareTo(LogWritable o) {
    if (requestPage.compareTo(o.requestPage) == 0) {
      return timestamp.compareTo(o.timestamp);
    } else
      return requestPage.compareTo(o.requestPage);
  }

  public int hashCode()
  {
    return requestPage.hashCode();
  }

  /*
   * Getters & Setters
   */

  public Text getUserIP() {
    return userIP;
  }

  public Text getTimestamp() {
    return timestamp;
  }

  public Text getRequestType() {
    return requestType;
  }

  public Text getRequestPage() {
    return requestPage;
  }

  public Text getBrowser() {
    return  browser;
  }

  public IntWritable getResponseSize() {
    return responseSize;
  }

  public IntWritable getStatus() {
    return status;
  }
}