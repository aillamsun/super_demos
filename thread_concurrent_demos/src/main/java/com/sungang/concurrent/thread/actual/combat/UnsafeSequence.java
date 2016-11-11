package com.sungang.concurrent.thread.actual.combat;


/**
 * Created by sungang on 2016/3/30.11:14
 *
 * 非线程安全  如果需要保证线程安全需要添加同步锁
 */
//@NotThreadSafe
public class UnsafeSequence {

    private int value;

    public int getNext(){
        return value++;
    }


    public static void main(String[] args) {
        //主线程

        final UnsafeSequence sequence = new UnsafeSequence();

//        for (int i = 0; i < 10; i++){
//            System.out.println(Thread.currentThread().getName() + " : " + sequence.getNext());
//        }


        for (int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++){
                        System.out.println(Thread.currentThread().getName() + " : "+ sequence.getNext());
                    }
                }
            },"t"+i).start();
        }
    }

}
