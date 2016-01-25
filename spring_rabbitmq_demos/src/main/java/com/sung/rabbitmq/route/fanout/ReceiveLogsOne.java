package com.sung.rabbitmq.route.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsOne {

	private static final String EXCHANGE_NAME = "fanout_logs";

	public static void main(String[] args) throws Exception {
		Connection connection = null;
		Channel channel = null;

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();
			channel = connection.createChannel();
			// 指定exchange的类型为fanout
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "");

			System.out
					.println(" ReceiveLogsOne----->Waiting for messages. To exit press CTRL+C");

			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);

			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println("Received ':'" + message + "'");
			}
		} catch (Exception e) {
		}
	}
}
