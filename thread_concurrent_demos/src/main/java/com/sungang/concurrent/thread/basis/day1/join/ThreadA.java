package com.sungang.concurrent.thread.basis.day1.join;

/**
 * Created by sungang on 2016/3/29.17:18
 */
public class ThreadA extends Thread {
    public ThreadA(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.printf("%s start\n", this.getName());
        // 延时操作
        for(int i=0; i <1000000; i++);

        System.out.printf("%s finish\n", this.getName());
    }
}
