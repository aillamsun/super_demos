package com.sungang.concurrent.thread.lock.semaphore;

/**
 * Created by sungang on 2016/11/11.
 */
public class Job implements Runnable {

    private PrintQueue printQueue;


    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Going to print a job");
        printQueue.printJob(new Object());
        System.out.println(Thread.currentThread().getName() + " the document has bean printed");
    }
}
