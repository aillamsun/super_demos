package com.sung.rabbitmq.one2one.example01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 单发送单接收
 * 
 */
public class Recv {

	private final static String QUEUE_NAME = "hello_world";
	public static final boolean autoAck = false;
	public static final boolean durable = true;

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// factory.setUsername("sungang");
		// factory.setPassword("sungang");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		System.out.println("Wait for message : ");
		channel.basicQos(1); // 消息分发处理

		QueueingConsumer consumer = new QueueingConsumer(channel);
		/**
		 * autoAck false：设置确认消息，true表示接收到消息之后，将返回给服务端确定消息
		 */
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
		while (true) {
			Thread.sleep(500);
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("Message Receved : " + message);
			// 设置消息确认机制，如将如下代码注释掉，
			// 则一旦将autoAck关闭之后，一定要记得处理完消息之后，
			//向服务器确认消息。否则服务器将会一直转发该消息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}
