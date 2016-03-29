package com.sungang.concurrent.thread;

import java.util.concurrent.*;

public class FutureAndCallableTest {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<Integer> future = exec.submit(new Task());
		exec.shutdown();
		long t = System.currentTimeMillis();
		try {
			System.out.println(future.get(3100, TimeUnit.MILLISECONDS));
			System.out.println("计算结果 : " + future.get());
		}catch (TimeoutException e){
			future.cancel(true);
			e.printStackTrace();
			System.err.println("Interrupte time is " + (System.currentTimeMillis() - t));
		}
	}
}
