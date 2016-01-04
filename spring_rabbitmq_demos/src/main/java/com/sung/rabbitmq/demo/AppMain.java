/*
package com.sung.rabbitmq.demo;

import com.sungang.rabbitmq.base.MQQueueNameBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.sung.rabbitmq.base.MQQueueNameBean;

public class AppMain {
	
	ClassPathXmlApplicationContext context = null;
	RabbitTemplate rabbitTemplate = null;
	String test_msg = "haha";
	MQQueueNameBean mqQueueNameBean = null;
	
	public void startUp() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-config-rabbitmq-server.xml");
		rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);
		mqQueueNameBean = context.getBean("mqConfigBean",MQQueueNameBean.class );
	}
	
	*/
/**
	 * 同步返回mq响应消息
	 *//*

	public void testSync() {
		Object response = rabbitTemplate.convertSendAndReceive("",mqQueueNameBean.getTestSpringMqSyncDemo(),test_msg);
		System.out.println(String.valueOf(response));
	}
	*/
/**
	 * 异步
	 *//*

	public void testAsync() {
		rabbitTemplate.convertAndSend("", mqQueueNameBean.getTestSpringMqAsyncDemo(),test_msg);
	}
}
*/
