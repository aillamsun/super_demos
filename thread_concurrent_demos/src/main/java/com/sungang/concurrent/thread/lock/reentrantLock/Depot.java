package com.sungang.concurrent.thread.lock.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sungang on 2016/11/11.
 */
public class Depot {

    private int depotSize;     //仓库大小
    private Lock lock;         //独占锁


    public Depot() {
        depotSize = 0;
        lock = new ReentrantLock();
    }

    /**
     * 商品入库
     *
     * @param value
     */
    public void put(int value) {
        try {
            lock.lock();
            depotSize += value;
            System.out.println(Thread.currentThread().getName() + " put " + value + " ----> the depotSize: " + depotSize);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 商品出库
     *
     * @param value
     */
    public void get(int value) {
        try {
            lock.lock();
            depotSize -= value;
            System.out.println(Thread.currentThread().getName() + " get " + value + " ----> the depotSize: " + depotSize);
        } finally {
            lock.unlock();
        }
    }
}
