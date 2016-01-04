package com.sung.rabbitmq.base;
/**
 * 用于初始化 zk 必要的一些队列名称
 * @author sungang
 *
 */
public class MQQueueNameBean {
	
	private String testSpringMqSyncDemo;
	private String testSpringMqAsyncDemo;
	public String getTestSpringMqSyncDemo() {
		return testSpringMqSyncDemo;
	}
	public void setTestSpringMqSyncDemo(String testSpringMqSyncDemo) {
		this.testSpringMqSyncDemo = testSpringMqSyncDemo;
	}
	public String getTestSpringMqAsyncDemo() {
		return testSpringMqAsyncDemo;
	}
	public void setTestSpringMqAsyncDemo(String testSpringMqAsyncDemo) {
		this.testSpringMqAsyncDemo = testSpringMqAsyncDemo;
	}

}
