package com.sungang.concurrent.thread.lock.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by sungang on 2016/11/11.
 */
public class PrintQueue {

    private final Semaphore semaphore;   //声明信号量

    public PrintQueue() {
        semaphore = new Semaphore(1);
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();//调用acquire获取信号量
            long duration = (long) (Math.random() * 10);
            System.out.println(Thread.currentThread().getName() + "-> PrintQueue : Printing a job during " + duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();  //释放信号量
        }
    }

}
