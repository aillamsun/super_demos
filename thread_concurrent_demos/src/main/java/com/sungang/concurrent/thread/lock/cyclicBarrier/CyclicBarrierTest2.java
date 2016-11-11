package com.sungang.concurrent.thread.lock.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by sungang on 2016/11/11.
 */
public class CyclicBarrierTest2 {

    private static CyclicBarrier barrier;

    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "达到...");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完成...");
        }
    }

    public static void main(String[] args) {
        barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("执行CyclicBarrier中的任务.....");
            }
        });


        for (int i = 1; i <= 5; i++) {
            new Thread1().start();
        }

    }

}
