package com.sungang.concurrent.thread.lock.phaser;

import java.util.concurrent.Phaser;

/**
 * 在这里，任务一开始并没有真正执行，而是等待三秒后执行。
 *
 * Created by sungang on 2016/11/11.
 */
public class PhaserTest2 {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);        //相当于CountDownLatch(1)
        //五个子任务
        for (int i = 0; i < 3; i++) {
            Task_02 task = new Task_02(phaser);
            Thread thread = new Thread(task, "PhaseTest_" + i);
            thread.start();
        }

        try {
            //等待3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arrive();        //countDownLatch.countDown()
    }

    static class Task_02 implements Runnable {
        private final Phaser phaser;

        Task_02(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.awaitAdvance(phaser.getPhase());        //countDownLatch.await()
            System.out.println(Thread.currentThread().getName() + "执行任务...");
        }
    }
}
