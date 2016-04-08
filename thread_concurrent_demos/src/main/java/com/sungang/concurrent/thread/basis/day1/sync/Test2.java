package com.sungang.concurrent.thread.basis.day1.sync;

/**
 * Created by sungang on 2016/3/29.16:39
 */
public class Test2 extends Thread {

    public Test2(String name) {
        super(name);
    }


    @Override
    public void run() {
        synchronized(this) {

            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " loop " + i);
                }
            }catch (InterruptedException e){

            }
        }
    }

    public static void main(String[] args) {
        Test2 t1 = new Test2("T1");
        Test2 t2 = new Test2("T2");

        t1.start();
        t2.start();
    }
}
