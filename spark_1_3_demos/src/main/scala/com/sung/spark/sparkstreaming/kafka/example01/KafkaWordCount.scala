package com.sung.spark.sparkstreaming.kafka.example01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

/**
 * Created by sungang on 2015/12/28.10:47
 * spark-submit --class com.sung.spark.sparkstreaming.kafka.example01.KafkaWordCount --master local[2] --num-executors 2 --driver-memory 512m --executor-memory 512m --executor-cores 1 /usr/spark_lean-1.0-SNAPSHOT.jar master:2181 group1 sungang 1
 */
object KafkaWordCount {

  def main(args: Array[String]) {

    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }
    val Array(zkQuorum, group, topics, numThreads) = args
    val ssc =  new StreamingContext(new SparkConf().setAppName("KafkaWordCount"), Seconds(2))
    ssc.checkpoint(" ")
    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}

//object SQLContextSingleton {
//  @transient  private var instance: SQLContext = _
//  def getInstance(sparkContext: SparkContext): SQLContext = {
//    if (instance == null) {
//      instance = new SQLContext(sparkContext)
//    }
//    instance
//  }
//}
