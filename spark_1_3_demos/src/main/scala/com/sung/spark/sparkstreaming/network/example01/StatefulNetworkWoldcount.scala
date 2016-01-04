package com.sung.spark.sparkstreaming.network.example01

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
/**
 * 在原来状态上统计 累计计算
 * 统计之前所有数据记录结果
 * spark-submit --class com.sung.spark.sparkstreaming.network.example01.StatefulNetworkWoldcount --master local[2] --num-executors 2 --driver-memory 512m --executor-memory 512m --executor-cores 1 /usr/spark_lean-1.0-SNAPSHOT.jar 192.168.3.200 9999 10

 */
object StatefulNetworkWoldcount {
  def main(args: Array[String]): Unit = {
    
    if (args.length < 3) {
      System.err.println("Usage: StatefulNetworkWoldcount<hastname> <port> <seconds>\n" + 
          "In local mode,<master> should be 'local[n]' with n > 1")
      System.exit(1);
    }
    
    val updateFunc = (values:Seq[Int],state:Option[Int]) =>{
      val currentCount = values.foldLeft(0)(_ + _)
      val previousCount = state.getOrElse(0)
      Some(currentCount+previousCount)
    }
    
    //新建 Spark StreamingContext
    val sparkConf = new SparkConf().setAppName("StatefulNetworkWoldcount")
    val ssc = new StreamingContext(sparkConf, Seconds(args(2).toInt))
    ssc.checkpoint(".")
    val lines = ssc.socketTextStream(args(0),args(1).toInt)
    var words = lines.flatMap { line => line.split(" ") }
    var wordDstream = words.map { line => (line,1) }//.reduceByKey(_ + _)
    
    var stateDstream = wordDstream.updateStateByKey[Int](updateFunc)
    stateDstream.print();
    ssc.start();
    ssc.awaitTermination();
  }
}