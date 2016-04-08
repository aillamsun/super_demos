package com.sungang.concurrent.thread.basis.day1.join;

/**
 * Created by sungang on 2016/3/29.17:16
 */
public class Fathe extends Thread{


    @Override
    public void run() {
        Son son = new Son();
        son.start();
        try {
            son.join();
        }catch (InterruptedException e){

        }
    }
}
