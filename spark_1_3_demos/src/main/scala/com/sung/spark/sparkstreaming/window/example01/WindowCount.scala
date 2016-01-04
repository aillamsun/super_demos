package com.sung.spark.sparkstreaming.window.example01

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
/**
 * 计算一段时间内统计
 * spark-submit --class com.sung.spark.sparkstreaming.window.example01.WindowCount --master local[2] --num-executors 2 --driver-memory 512m --executor-memory 512m --executor-cores 1 /usr/spark_lean-1.0-SNAPSHOT.jar 192.168.3.200 9999 10 30 30
 */
object WindowCount {
  
   def main(args: Array[String]): Unit = {
    if (args.length < 3) {
      System.err.println("Usage: WindowCount<hastname> <port> <interval> <windowlength> <slideInterval> \n" + 
          "In local mode,<master> should be 'local[n]' with n > 1")
      System.exit(1);
    }
    //新建 Spark StreamingContext
    val sparkConf = new SparkConf().setAppName("WindowCount")
    val ssc = new StreamingContext(sparkConf, Seconds(args(2).toInt))
    ssc.checkpoint(".")
    val lines = ssc.socketTextStream(args(0),args(1).toInt,StorageLevel.MEMORY_ONLY_SER)
    var words = lines.flatMap { line => line.split(" ") }
    
    val wordCounts = words.map { line => (line,1)}.reduceByKeyAndWindow(_+_,_-_,Seconds(args(3).toInt), Seconds(args(4).toInt))
    
    //排序
    val sortedWordCounts = wordCounts.map{case(char,count) =>(count,char)}.transform(_.sortByKey(false)).map{case(char,count) => (count,char)}

    //wordCounts.print();
    sortedWordCounts.print();
    ssc.start();
    ssc.awaitTermination();
    
  }
}