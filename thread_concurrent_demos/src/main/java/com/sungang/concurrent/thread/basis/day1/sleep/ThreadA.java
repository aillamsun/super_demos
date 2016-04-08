package com.sungang.concurrent.thread.basis.day1.sleep;

/**
 * Created by sungang on 2016/3/29.17:13
 */
public class ThreadA extends Thread {

    public ThreadA(String name) {
        super(name);
    }

    @Override
    public synchronized void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s: %d\n", this.getName(), i);
                // i能被4整除时，休眠100毫秒
                if (i % 4 == 0)
                    Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");
        t1.start();
    }
}
