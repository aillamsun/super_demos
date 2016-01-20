package com.sungang.concurrent.thread;

import java.util.Random;
import java.util.concurrent.Callable;

public class TaskJob implements Callable<Integer>{
	public Integer call() throws Exception {
		int ran = new Random().nextInt(1000);  
		//Thread.sleep(ran);
		//System.out.println(Thread.currentThread().getName() + " 休息了 " + ran);
		return ran;  
	}
}
