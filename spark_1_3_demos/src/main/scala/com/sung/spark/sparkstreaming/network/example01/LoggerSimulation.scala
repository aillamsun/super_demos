package com.sung.spark.sparkstreaming.network.example01

import java.util.Random

import scala.collection.mutable.ListBuffer
import java.net.ServerSocket
import java.io.PrintWriter

object LoggerSimulation {
    
    def generateContent(index:Int): String = {
        val charList = ListBuffer[Char]()
        for(i <- 65 to 90){
          charList += i.toChar
        }
        val charArray = charList.toArray
        charArray(index).toString
    }
    
    def index = {
      val rdm = new Random()
      rdm.nextInt(7);
    }
    
    def main(args: Array[String]): Unit = {
      if (args.length != 2) {
        System.err.print("Usage:<port> <millisecomd>");
        System.exit(1);
      }
      
      val listener = new ServerSocket(args(0).toInt)
      println("This client port is: "+ args(0))
      while(true){
        val socket = listener.accept();
        new Thread(){
          override def run = {
            println("Got client connected from " + socket.getInetAddress)
            val out = new PrintWriter(socket.getOutputStream(),true)
            while(true){
              Thread.sleep(args(1).toLong)
              val content = generateContent(index)
              println(content)
              out.write(content + "\n")
              out.flush()
            }
            socket.close()
          }
        }.start();
      }
    }
}