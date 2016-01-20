package com.sungang.concurrent.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 学生上车
 */
public class CountDownLatchTest2 {
	/**
	 * 等车的学生数
	 */
	public static int numberOfPeople = 10;
	/**
	 * 车开的标志
	 */
	public static boolean isGone = false;
	/**
	 * 车等的时间
	 */
	public static int carWaitTime = 5;

	public static void main(String[] args) throws InterruptedException {

		CountDownLatch waitStudentsGetOn = new CountDownLatch(numberOfPeople);
		ExecutorService exec = Executors.newFixedThreadPool(10);
		exec.submit(new GetOn(waitStudentsGetOn));
		
		waitStudentGetOn(waitStudentsGetOn);
		
		driveHome();
		
		exec.shutdown();
	}

	private static void waitStudentGetOn(CountDownLatch waitStudentsGetOn)
			throws InterruptedException {
		System.out.println("赶紧的,抓紧时间上车..");
		/**
		 * 等待5秒，学生还没上车，车就开走
		 */
		waitStudentsGetOn.await(carWaitTime, TimeUnit.SECONDS);//

	}

	private static void driveHome() throws InterruptedException {
		System.out.println("开车，鞋儿破 帽儿破 身上的袈裟破 你笑我 他笑我 一把扇儿破");
		/**
		 * 车开走了
		 */
		isGone = true;
	}
}

class GetOn implements Runnable {

	private CountDownLatch waitStudentsGetOn;

	public GetOn(CountDownLatch waitStudentsGetOn) {
		this.waitStudentsGetOn = waitStudentsGetOn;
	}

	public void run() {
		
		 for (int i = 0; i < CountDownLatchTest2.numberOfPeople; i++) {
			 try {
				 //if 车开走了
				 if(CountDownLatchTest2.isGone){
					 System.out.println("妈的，还差："+waitStudentsGetOn.getCount()+" 个娃没上车呢.怎么车走了啊");
					 break;
				 }
				 boolean goonSuccess = new Student(i+1).getOn();//顺序上车
				 //如果上车成功，则计数器减一
				 if(goonSuccess){
					 waitStudentsGetOn.countDown();
				 }
				 
			} catch (Exception e) {
				
			}finally{
				 if(waitStudentsGetOn.getCount()!=0l){
					 System.out.println("还差："+(waitStudentsGetOn.getCount())+" 个没上车");
				 }else{
					 System.out.println("都上车了");
				 }
			}
		 }
	}
}


class Student{
	
    private int myNum;//学生编号
  
    public Student(int num){
            this.myNum = num;
     }
     
     //上车
     public boolean getOn() throws InterruptedException{
    	 /**
    	  * 上车使用的时间，随机
    	  */
         Thread.currentThread().sleep(new Random().nextInt(2)*1000);
         //如果车开走了
         if(CountDownLatchTest2.isGone){
             return false;//不能上了，上车失败
         }
         System.out.print("编号为:"+myNum+"的同学上车了..");
         return true;
     }
}
