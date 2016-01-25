package com.sung.rabbitmq.one2one.example02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 单发送多接收
 */
public class NewTask {
	private static final String TASK_QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

		String message = getMessage(args);
		channel.basicPublish("", TASK_QUEUE_NAME,
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "Hello World!...";
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
