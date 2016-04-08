package com.sungang.concurrent.thread.basis.day1.sync;

/**
 * Created by sungang on 2016/3/29.16:35
 *
 * run()方法中存在“synchronized(this)代码块”，而且t1和t2都是基于"demo这个Runnable对象"创建的线程。这就意味着，我们可以将synchronized(this)中的this看作是“demo这个Runnable对象”；因此，线程t1和t2共享“demo对象的同步锁”。
 * 所以，当一个线程运行的时候，另外一个线程必须等待“运行线程”释放“demo的同步锁”之后才能运行
 */
public class Test1 implements Runnable {

    @Override
    public void run() {
        synchronized (this){
            try {

                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " loop " + i);
                }
            } catch (InterruptedException ee){

            }
        }
    }

    public static void main(String[] args) {
        Test1 demo = new Test1();

        Thread t1 = new Thread(demo, "t1");  // 新建“线程t1”, t1是基于demo这个Runnable对象
        Thread t2 = new Thread(demo, "t2");  // 新建“线程t2”, t2是基于demo这个Runnable对象

        t1.start();
        t2.start();
    }
}
