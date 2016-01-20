package com.sungang.concurrent.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这个示例中BoundedBuffer是一个固定长度的集合，这个在其put操作时，如果发现长度已经达到最大长度，
 * 那么会等待notFull信号，如果得到notFull信号会像集合中添加元素，并发出notEmpty的信号，
 * 而在其take方法中如果发现集合长度为空，那么会等待notEmpty的信号，同时如果拿到一个元素， 那么会发出notFull的信号
 */
public class ConditionAndReentrantLockTest2 {

	public static void main(String[] args) {
		final BoundedBuffer boundedBuffer = new BoundedBuffer();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("t1 run");
				for (int i = 0; i < 10; i++) {
					try {
						boundedBuffer.put(Integer.valueOf(i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						Object val = boundedBuffer.take();
						System.out.println(val);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		t1.start();
		t2.start();
	}

	static class BoundedBuffer {

		final Lock lock = new ReentrantLock();
		final Condition notFull = lock.newCondition();
		final Condition notEmpty = lock.newCondition();

		final Object[] items = new Object[10];

		int putptr, takeptr, count;

		public void put(Object x) throws InterruptedException {
			System.out.println("Put Wait Lock...");
			lock.lock();
			try {
				while (count == items.length) {
					System.out.println("Buffer full,Please Waiting...");
					// 等待...
					notFull.await();
				}

				items[putptr] = x;
				if (++putptr == items.length) {
					putptr = 0;
				}
				++count;
				notEmpty.signal();

			} catch (Exception e) {
			} finally {
				notEmpty.signal();
			}
		}

		public Object take() throws InterruptedException {
			lock.lock();
			try {
				while (count == 0)
					notEmpty.await();
				Object x = items[takeptr];
				if (++takeptr == items.length) {
					takeptr = 0;
				}
				--count;
				notFull.signal();
				return x;
			} finally {
				lock.unlock();
			}
		}

	}
}
