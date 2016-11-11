package com.sungang.concurrent.thread.lock.conditionReentrantLock;

/**
 * Created by sungang on 2016/11/11.
 */
public class ConditionAndReentrantLockTest2 {


    public static void main(String[] args) {
        Depot depot = new Depot();

        Producer producer = new Producer(depot);
        Customer customer = new Customer(depot);


        producer.produce(10);
        customer.consume(5);
        producer.produce(15);
        customer.consume(10);
        customer.consume(15);
        producer.produce(10);
    }
}
