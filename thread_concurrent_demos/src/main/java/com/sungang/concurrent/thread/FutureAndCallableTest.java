package com.sungang.concurrent.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureAndCallableTest {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<Integer> future = exec.submit(new Task());
		exec.shutdown();
		System.out.println("计算结果 : " + future.get());
		
	}
}
