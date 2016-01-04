package com.sung.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 异步
 */
public class TestSpringMqAsyncDemo implements ChannelAwareMessageListener {

	private RabbitTemplate rabbitTemplate;

	private static MessageConverter messageConverter = new SimpleMessageConverter();

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void onMessage(Message requestMessage, Channel channel)
			throws Exception {
		// 接收到的消息
		/* Object obj = messageConverter */
		String message = null;
		try {
			message = new String(requestMessage.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			System.out.println(" [Mq] Async Received -->>: " + message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//手动 ack
				channel.basicAck(requestMessage.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
