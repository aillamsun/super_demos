package com.sungang.concurrent.thread.basis.day1;

/**
 * Created by sungang on 2016/3/29.16:24
 */
public class MyRunnable implements Runnable {

    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++){
            if (this.ticket > 0){
                System.out.println(Thread.currentThread().getName()+" 卖票：ticket "+this.ticket--);
            }
        }
    }

    public static void main(String[] args) {

        MyRunnable mr = new MyRunnable();

        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        Thread t3 = new Thread(mr);

        t1.start();
        t2.start();
        t3.start();
    }
}
