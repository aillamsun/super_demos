package com.sungang.concurrent.thread.basis.day1.join;

/**
 * Created by sungang on 2016/3/29.17:18
 */
public class JoinTest  {

    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");

        try {
            t1.start(); // 启动“线程t1”
            t1.join();  // 将“线程t1”加入到“主线程main”中，并且“主线程main()会等待它的完成”
            System.out.printf("%s finish\n", Thread.currentThread().getName());
        }catch (InterruptedException e){

        }
    }


}
