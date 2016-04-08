package com.sungang.concurrent.thread.basis.day1.priority;

/**
 * Created by sungang on 2016/3/29.17:28
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()
                + "(isDaemon=" + Thread.currentThread().isDaemon() + ")");


        Thread t1 = new MyDaemonThread("t1");    // 新建t1
        Thread t2 = new MyNotDaemonThread("t2");    // 新建t2

        t1.setDaemon(true);

        t1.start();
        t2.start();

    }
}
