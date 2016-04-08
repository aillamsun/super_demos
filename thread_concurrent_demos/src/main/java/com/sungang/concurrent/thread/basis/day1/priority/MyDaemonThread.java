package com.sungang.concurrent.thread.basis.day1.priority;

/**
 * Created by sungang on 2016/3/29.17:25
 */
public class MyDaemonThread extends Thread{

    public MyDaemonThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<5; i++) {
                Thread.sleep(3);
                System.out.println(this.getName() +"(isDaemon="+this.isDaemon()+ ")" +", loop "+i);
            }
        }catch (InterruptedException e){

        }
    }

    public static void main(String[] args) {

    }
}
