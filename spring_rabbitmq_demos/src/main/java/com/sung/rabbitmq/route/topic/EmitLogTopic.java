package com.sung.rabbitmq.route.topic;

import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();
			channel = connection.createChannel();
			// 指定exchange的类型为topic
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			String[] msg = new String[] { "RabbitMQ Test!",
					"This is a Topic Exchange Model!" };
			// 获取发送消息的routing key
			String[] routType = new String[] { "anonymous.info",
					"anonymous.warning", "anonymous.error", "*.info",
					"anonymous.#" };
			String routingKey = getRouting(routType);
			// 获取发送消息
			String message = getMessage(msg);
			channel.basicPublish(EXCHANGE_NAME, routingKey, null,
					message.getBytes());
			System.out.println("EmitLogTopic---->Sent '" + routingKey + "':'"
					+ message + "'");
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

	private static String getRouting(String[] strings) {
		Random random = new Random();
		int ranVal = random.nextInt(5);
		return strings[ranVal];
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 2)
			return "Hello World!";
		return joinStrings(strings, " ", 1);
	}

	private static String joinStrings(String[] strings, String delimiter,
			int startIndex) {
		int length = strings.length;
		if (length == 0)
			return "";
		if (length < startIndex)
			return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for (int i = startIndex + 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
