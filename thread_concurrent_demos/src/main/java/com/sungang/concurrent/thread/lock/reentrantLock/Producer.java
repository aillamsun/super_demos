package com.sungang.concurrent.thread.lock.reentrantLock;

/**
 * 生产
 * Created by sungang on 2016/11/11.
 */
public class Producer {

    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void produce(final int value) {
        new Thread() {
            public void run() {
                depot.put(value);
            }
        }.start();
    }
}
