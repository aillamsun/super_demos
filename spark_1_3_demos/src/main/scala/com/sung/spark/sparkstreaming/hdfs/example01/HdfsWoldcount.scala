package com.sung.spark.sparkstreaming.hdfs.example01

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel
import org.apache.spark.SparkConf
/**
 * 获取HDFS 目录数据 统计
 * spark-submit --class com.sung.spark.sparkstreaming.hdfs.example01.HdfsWoldcount --master local[2] --num-executors 2 --driver-memory 512m --executor-memory 512m --executor-cores 1 /usr/spark_lean-1.0-SNAPSHOT.jar hdfs://master:9000/tmp/spark-test/1223 10
 */
object HdfsWoldcount {
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println("Usage: HdfsWoldcount<dir> <seconds>\n" +
          "In local mode,<master> should be 'local[n]' with n > 1")
      System.exit(1);
    }
    //新建 Spark StreamingContext
    val sparkConf = new SparkConf().setAppName("HdfsWoldcount")
    val ssc = new StreamingContext(sparkConf, Seconds(args(1).toInt))
    val lines = ssc.textFileStream(args(0))
    var words = lines.flatMap { line => line.split(" ") }
    var wordcounts = words.map { line => (line,1) }.reduceByKey(_ + _)
    wordcounts.print();
    ssc.start();
    ssc.awaitTermination();
  }
}