package com.sungang.concurrent.thread.basis.day1.sync;

/**
 * Created by sungang on 2016/3/29.16:50
 * synchronized代码块可以更精确的控制冲突限制访问区域，有时候表现更高效率
 */
public class Demo {

    double d = 1;


    public synchronized void synMethod() {
       d += (Math.PI + Math.E) / d;
    }

    public void synBlock() {
        synchronized( this ) {
            d += (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        long start, diff;
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++){
            demo.synMethod();
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("synMethod() : "+ diff);

        start = System.currentTimeMillis();

        for (int i = 0; i < 100000000; i++){
            demo.synBlock();
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("synBlock()  : "+ diff);
    }
}
