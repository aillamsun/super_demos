package com.sungang.concurrent.thread.basis.day1.sync;

/**
 * Created by sungang on 2016/3/29.16:47
 * 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，
 * 其他线程对“该对象”的其他的“synchronized方法”或者“synchronized代码块”的访问将被阻塞
 */
public class Count2 {

    // 含有synchronized同步块的方法
    public void synMethod() {
        synchronized (this) {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                }
            } catch (InterruptedException ie) {

            }
        }
    }

    // 非同步的方法
    public void nonSynMethod() {
        synchronized (this){
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " nonSynMethod loop " + i);

                }
            } catch (InterruptedException ie) {

            }
        }
    }


    public static void main(String[] args) {
        final Count2 count = new Count2();
        // 新建t1, t1会调用“count对象”的synMethod()方法
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                count.synMethod();
            }
        });

        // 新建t2, t2会调用“count对象”的nonSynMethod()方法
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                count.nonSynMethod();
            }
        });

        t1.start();  // 启动t1
        t2.start();  // 启动t2
    }

}
