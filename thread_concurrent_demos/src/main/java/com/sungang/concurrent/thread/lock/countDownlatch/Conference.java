package com.sungang.concurrent.thread.lock.countDownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sungang on 2016/11/11.
 */
public class Conference implements Runnable {

    private final CountDownLatch countDown;

    public Conference(int count) {
        countDown = new CountDownLatch(count);
    }

    /**
     * 与会人员到达，调用arrive方法，到达一个CountDownLatch调用countDown方法，锁计数器-1
     *
     * @param name
     */
    public void arrive(String name) {
        System.out.println(name + "到达.....");
        //调用countDown()锁计数器 - 1
        countDown.countDown();
        System.out.println("还有 " + countDown.getCount() + "没有到达...");
    }

    @Override
    public void run() {
        System.out.println("准备开会，参加会议人员总数为：" + countDown.getCount());
        //调用await()等待所有的与会人员到达
        try {
            countDown.await();
        } catch (InterruptedException e) {
        }
        System.out.println("所有人员已经到达，会议开始.....");
    }
}
