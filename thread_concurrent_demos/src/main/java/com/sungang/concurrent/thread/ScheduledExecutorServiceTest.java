package com.sungang.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

		Runnable task1 = new Runnable() {
			public void run() {
				System.out.println("Taskrepeating.");
			}
		};
		//1秒后 执行  每隔2秒执行一次
		final ScheduledFuture future1 = service.scheduleAtFixedRate(task1, 1,2, TimeUnit.SECONDS);
//		System.out.println(future1.get().toString());
		
		//future1 任务执行时间为10秒
		ScheduledFuture future2 = service.schedule(new Callable<String>() {
			public String call() throws Exception {
				  future1.cancel(true);
                  return "taskcancelled!";
			}

		}, 10, TimeUnit.SECONDS);
		
		System.out.println(future2.get());
		service.shutdown();
	}
}
