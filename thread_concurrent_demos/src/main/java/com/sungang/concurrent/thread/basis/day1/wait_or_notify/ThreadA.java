package com.sungang.concurrent.thread.basis.day1.wait_or_notify;

/**
 * Created by sungang on 2016/3/29.16:59
 */
public class ThreadA extends Thread{

    public ThreadA(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()+" call notify()");
            notify();
        }
    }

    public static void main(String[] args) {

        ThreadA t1 = new ThreadA("t1");

        synchronized (t1){
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start t1");
                t1.start();

                // 主线程等待t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName()+" wait()");
                t1.wait();
                System.out.println(Thread.currentThread().getName()+" continue");
            }catch (InterruptedException e) {

            }
        }
    }
}
