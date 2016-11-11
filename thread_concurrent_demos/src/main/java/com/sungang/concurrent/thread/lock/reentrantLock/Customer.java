package com.sungang.concurrent.thread.lock.reentrantLock;

/**
 * Created by sungang on 2016/11/11.
 */
public class Customer {

    private Depot depot;

    public Customer(Depot depot) {
        this.depot = depot;
    }


    public void consume(final int value) {
        new Thread() {
            @Override
            public void run() {
                depot.get(value);
            }
        }.start();
    }
}
