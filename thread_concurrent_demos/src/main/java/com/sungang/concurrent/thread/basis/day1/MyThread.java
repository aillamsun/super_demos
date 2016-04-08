package com.sungang.concurrent.thread.basis.day1;

/**
 * Created by sungang on 2016/3/29.16:20
 */
public class MyThread extends Thread {


    private int ticket = 10;

    @Override
    public void run() {

        for (int i = 0; i < 20; i++){
            if (this.ticket > 0){
                System.out.println(this.getName()+" 卖票：ticket "+this.ticket--);
            }
        }
    }


    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();

        t1.start();
        t2.start();
        t3.start();
    }
}
