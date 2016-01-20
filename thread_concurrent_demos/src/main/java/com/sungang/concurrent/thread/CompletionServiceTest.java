package com.sungang.concurrent.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

public class CompletionServiceTest {

	public static void main(String[] args) {
		/**
		 * 内部维护11个线程的线程池
		 */
		ExecutorService exec = Executors.newFixedThreadPool(11);
		/**
		 * 容量为10的阻塞队列
		 */

		final BlockingQueue<Future<Integer>> queue = new LinkedBlockingDeque<Future<Integer>>(1000);
		/**
		 * 实例化CompletionService  
		 */
		final CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(exec,queue);
		
		
		/**
		 * 模拟瞬间产生10个任务，且每个任务执行时间不一致 
		 */
		for (int i = 0; i < 1000; i++)  {
			completionService.submit(new TaskJob());
		}
		/**
		 * 立即输出结果
		 * 最先执行完成的直接返回，并不需要按任务提交的顺序执行，如果需要写个高并发的程序，且每个任务需要返回执行结果，这是个相当不错的选择
		 */
		long s = System.currentTimeMillis();  
		for (int i = 0; i < 1000; i++) {
			try {
				/**
				 * 谁最先执行完成，直接返回
				 */
				Future<Integer> f = completionService.take();  
				System.out.println("Result : " + f.get()); 
			} catch (Exception e) {
				
			}
		}
		System.out.println("执行任务消耗了 ：" + (System.currentTimeMillis() - s) +"毫秒");
		exec.shutdown();  
	}
}
