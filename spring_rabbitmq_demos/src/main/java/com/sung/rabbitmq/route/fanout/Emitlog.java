package com.sung.rabbitmq.route.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Emitlog {

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

			String[] msg = new String[] { "RabbitMQ Test!",
					"This is a Topic Exchange Model!" };

			// 获取发送消息
			String message = getMessage(msg);
			channel.basicPublish(EXCHANGE_NAME, "",
					MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

			System.out.println("Emitlog---->Sent :" + message);

			channel.close();
			connection.close();
		} catch (Exception e) {
		}
	}

	private static String getMessage(String[] args) {
		if (args.length < 1)
			return "Hello World !";
		return joinString(args, " ");
	}

	private static String joinString(String[] args, String delimiter) {
		int length = args.length;
		if (length == 0)
			return "";

		StringBuilder words = new StringBuilder(args[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(args[i]);
		}
		return words.toString();
	}
}
