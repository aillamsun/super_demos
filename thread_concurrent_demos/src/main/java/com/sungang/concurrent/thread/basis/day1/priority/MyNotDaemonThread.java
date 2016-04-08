package com.sungang.concurrent.thread.basis.day1.priority;


/**
 * Created by sungang on 2016/3/29.17:26
 */
public class MyNotDaemonThread extends Thread {

    public MyNotDaemonThread(String name) {
        super(name);
    }


    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1);
                System.out.println(this.getName() + "(isDaemon=" + this.isDaemon() + ")" + ", loop " + i);
            }
        } catch (InterruptedException e) {
        }
    }
}
