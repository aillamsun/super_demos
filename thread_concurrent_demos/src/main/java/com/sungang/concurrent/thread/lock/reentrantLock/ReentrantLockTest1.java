package com.sungang.concurrent.thread.lock.reentrantLock;

/**
 * Created by sungang on 2016/11/11.
 */
public class ReentrantLockTest1 {


    public static void main(String[] args) {
        Depot depot = new Depot();

        Producer producer = new Producer(depot);
        Customer customer = new Customer(depot);

        producer.produce(10);
        customer.consume(5);
        producer.produce(10);
        customer.consume(15);



//        Thread-0 put 10 ----> the depotSize: 10
//        Thread-1 get 5 ----> the depotSize: 5
//        Thread-2 put 10 ----> the depotSize: 15
//        Thread-3 get 15 ----> the depotSize: 0
    }
}
