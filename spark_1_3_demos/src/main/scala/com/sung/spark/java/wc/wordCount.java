package com.sung.spark.java.wc;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Created by sungang on 2016/1/19.11:32
 */
public class wordCount {

    public static void main(String[] args) {

        //Create a java spark context
        SparkConf conf = new SparkConf();
        conf.setAppName("spark java wordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //Load input data
        //args[0] = "src/main/resources/wc.txt";
        JavaRDD<String> hdfsIn = sc.textFile(args[0]);

        //Split each lines into words
        JavaRDD<String> words = hdfsIn.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" "));
            }
        });

        //Transform into word and counts
        JavaPairRDD<String,Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });

        counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //Save output to hdfs files
        //args[1] = "src/main/resources/wcout.txt";
        counts.saveAsTextFile(args[1]);

    }
}
