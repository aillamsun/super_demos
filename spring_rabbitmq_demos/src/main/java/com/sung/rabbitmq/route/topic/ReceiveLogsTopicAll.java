package com.sung.rabbitmq.route.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsTopicAll {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();
			channel = connection.createChannel();
			// 指定exchange类型为topic
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			String queueName = channel.queueDeclare().getQueue();
			// 指定队列绑定key
			String[] msgType = new String[] { "anonymous.info",
					"anonymous.warning", "anonymous.error", "*.info",
					"anonymous.#" };
			
			
			if (msgType.length < 1) {
				System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
				System.exit(1);
			}
			// test1 :接收所有类型的消息
			for (String bindingKey : msgType) {
				channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
			}
			System.out.println("ReceiveLogsTopicAll --> Waiting for messages. To exit press CTRL+C");
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				String routingKey = delivery.getEnvelope().getRoutingKey();
				System.out.println("Received '" + routingKey + "':'"
						+ message + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}
