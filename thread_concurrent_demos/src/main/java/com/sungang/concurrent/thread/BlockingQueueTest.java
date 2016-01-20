package com.sungang.concurrent.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class BlockingQueueTest {
	
	/**
	 * 定义装苹果的篮子
	 */
	public static class Basket {
		//规定大小的BlockingQueue，其构造函数必须带一个int参数来指明其大小。其所含的对象是以FIFO（先入先出）顺序排序的。
		//BlockingQueue basket = new ArrayBlockingQueue<String>(3);
		//大小不定的BlockingQueue，若其构造函数带一个规定大小的参数，生成的BlockingQueue有大小限制，若不带大小参数，
		//所生成的BlockingQueue的大小由Integer.MAX_VALUE来决定。其所含的对象是以FIFO顺序排序的
		//BlockingQueue basket = new LinkedBlockingQueue<String>();
		//类似于LinkedBlockingQueue,但其所含对象的排序不是FIFO，而是依据对象的自然排序顺序或者是构造函数所带的
		//Comparator决定的顺序
		BlockingQueue basket = new PriorityBlockingQueue<String>();
		/**
		 * 生产苹果，放入篮子
		 * @throws InterruptedException
		 */
		public void produce(int i) throws InterruptedException {
			// put方法放入一个苹果，若basket满了，等到basket有位置
			String putVal = "An apple : " +i;
			System.out.println("Put Queue value : " + putVal);
			basket.put(putVal);
		}

		/**
		 * 消费苹果，从篮子中取走
		 * @return
		 * @throws InterruptedException
		 */
		public String consume() throws InterruptedException {
			// get方法取出一个苹果，若basket为空，等到basket有苹果为止
			return (String) basket.take();
		}

		public BlockingQueue getBasket() {
			return basket;
		}

		public void setBasket(BlockingQueue basket) {
			this.basket = basket;
		}
	}

	// 　测试方法
	public static void testBasket() {
		// 建立一个装苹果的篮子
		final Basket basket = new Basket();
		// 定义苹果生产者
		class Producer implements Runnable {

			public void run() {
				try {
					int i = 0;
					while (true) {
						i ++;
						// 生产苹果
						//System.out.println("生产者准备生产苹果：" + System.currentTimeMillis());
						basket.produce(i);
						//System.out.println("生产者生产苹果完毕：" + System.currentTimeMillis());
						// 休眠300ms
						Thread.sleep(1000);
					}
				} catch (InterruptedException ex) {
				}
			}
		}
		// 定义苹果消费者
		class Consumer implements Runnable {
			public void run() {
				try {
					while (true) {
						// 消费苹果
						//System.out.println("消费者准备消费苹果：" + System.currentTimeMillis());
						System.out.println("Get Queue value : " + basket.consume());
						//System.out.println("消费者消费苹果完毕：" + System.currentTimeMillis());
						// 休眠1000ms
						Thread.sleep(1000);
					}
				} catch (InterruptedException ex) {
				}
			}
		}
		ExecutorService service = Executors.newFixedThreadPool(3);
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		service.submit(producer);
		service.submit(consumer);
		// 程序运行5s后，所有任务停止
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		if (basket.getBasket().isEmpty()) {
			service.shutdownNow();
		}
	}
	/**
	 * Test
	 */
	public static void main(String[] args) {
		BlockingQueueTest.testBasket();
	}
}
