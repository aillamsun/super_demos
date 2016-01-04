package com.sung.zookeeper.zk;


import com.sung.base.common.utils.LogUtils;
import com.sung.zookeeper.zk.exception.ZkInterruptedException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ZkEventThread extends Thread {

	private final BlockingQueue<ZkEvent> _events = new LinkedBlockingQueue<ZkEvent>();

	private static final AtomicInteger _eventId = new AtomicInteger(0);

	private volatile boolean shutdown = false;

	static abstract class ZkEvent {
		private final String _description;

		public ZkEvent(String description) {
			_description = description;
		}

		public abstract void run() throws Exception;

		@Override
		public String toString() {
			return "ZkEvent[" + _description + "]";
		}
	}

	ZkEventThread(String name) {
		setDaemon(true);
		setName("ZkClient-EventThread-" + getId() + "-" + name);
	}

	@Override
	public void run() {
		LogUtils.logInfo("Starting ZkClient event thread.");
		try {
			while (!isShutdown()) {
				ZkEvent zkEvent = _events.take();
				int eventId = _eventId.incrementAndGet();
				LogUtils.logInfo("Delivering event #" + eventId + " " + zkEvent);
				try {
					zkEvent.run();
				} catch (InterruptedException e) {
					shutdown();
				} catch (ZkInterruptedException e) {
					shutdown();
				} catch (Exception e) {
					LogUtils.logError("Error handling event " + zkEvent, e);
				}
				LogUtils.logInfo("Delivering event #" + eventId + " done");
			}
		} catch (InterruptedException e) {
			LogUtils.logError("Terminate ZkClient event thread.", e);
		}
	}

	/**
	 * @return the shutdown
	 */
	public boolean isShutdown() {
		return shutdown || isInterrupted();
	}

	public void shutdown() {
		this.shutdown = true;
		this.interrupt();
	}

	public void send(ZkEvent event) {
		if (!isShutdown()) {
			LogUtils.logInfo("New event: " + event);
			_events.add(event);
		}
	}
}
