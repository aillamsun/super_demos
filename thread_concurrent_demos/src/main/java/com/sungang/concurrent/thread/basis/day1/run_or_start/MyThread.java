package com.sungang.concurrent.thread.basis.day1.run_or_start;

/**
 * Created by sungang on 2016/3/29.16:27
 */
public class MyThread extends Thread {

    public MyThread(String name) {
               super(name);
           }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is running");
    }


    public static void main(String[] args) {
        MyThread myThread = new MyThread("myThread");

        System.out.println(Thread.currentThread().getName()+" call mythread.run()");
        myThread.run();

        System.out.println(Thread.currentThread().getName()+" call mythread.start()");
        myThread.start();
    }
}
