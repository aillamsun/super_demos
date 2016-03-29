package com.sung.patterns.chain2;

/**
 * Created by sungang on 2016/2/18.10:23
 */
public class App {

    public static void main(String[] args) {
        Handler h1 = new GeneralManager();
        Handler h2 = new DeptManager();
        Handler h3 = new ProjectManager();

        h3.setNext(h2);
        h2.setNext(h1);


        String pm = h3.handerRequest("张三",200);
        System.out.println("pm = " + pm);

        String pm2 = h3.handerRequest("李四", 300);
        System.out.println("pm2 = " + pm2);

        System.out.println("---------------------------------------");

        String pm3 = h3.handerRequest("张三", 700);
        System.out.println("pm3 = " + pm3);

        String pm4 = h3.handerRequest("张三", 1500);
        System.out.println("pm4 = " + pm4);
    }
}
