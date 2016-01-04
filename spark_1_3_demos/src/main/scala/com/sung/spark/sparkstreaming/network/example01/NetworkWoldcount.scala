package com.sung.spark.sparkstreaming.network.example01

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel
import org.apache.spark.SparkConf
/**
 * 固定时间统计接收的数据
 * args(1) <hastname> scoket host
 * args(2) <port> scoket port
 * args(3) <seconds> 多久统计一次
 * spark on yarn
 *  spark-submit --class com.sung.spark.sparkstreaming.network.example01.NetworkWoldcount --master local[2] --num-executors 2 --driver-memory 512m --executor-memory 512m --executor-cores 1 /usr/spark_lean-1.0-SNAPSHOT.jar 192.168.3.200 9999 10
 * 
 */
object NetworkWoldcount {
  def main(args: Array[String]): Unit = {
    if (args.length < 3) {
      System.err.println("Usage: NetworkWoldcount<hastname> <port> <seconds>\n" + 
          "In local mode,<master> should be 'local[n]' with n > 1")
      System.exit(1);
    }
    //新建 Spark StreamingContext
    val sparkConf = new SparkConf().setAppName("NetworkWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(args(2).toInt))
    val lines = ssc.socketTextStream(args(0),args(1).toInt,StorageLevel.MEMORY_ONLY_SER)
    var words = lines.flatMap { line => line.split(" ") }
    var wordcounts = words.map { line => (line,1) }.reduceByKey(_ + _)
    wordcounts.print();
    ssc.start();
    ssc.awaitTermination();
  }
}