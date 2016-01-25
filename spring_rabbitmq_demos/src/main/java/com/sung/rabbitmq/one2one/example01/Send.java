package com.sung.rabbitmq.one2one.example01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 单发送单接收
 */
public class Send {
	/**
	 * 消息队列名称
	 */
	private final static String QUEUE_NAME = "hello_world";
	public static final boolean DURABLE = true; // 消息队列持久化

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// factory.setUsername("sungang");
		// factory.setPassword("sungang");
		// 创建连接
		Connection connection = factory.newConnection();
		// 创建信道
		Channel channel = connection.createChannel();
		/**
		 * true：消息持久化设置 这样设置之后，服务器收到消息后就会立刻将消息写入到硬盘，就可以防止突然服务器挂掉，而引起的数据丢失了。
		 * 但是服务器如果刚收到消息，还没来得及写入到硬盘，就挂掉了，这样还是无法避免消息的丢失。
		 */
		channel.queueDeclare(QUEUE_NAME, DURABLE, false, false, null);
		// 发送消息
		String message = "Hello world_" + Math.random();

		// 消息持久化设置MessageProperties.PERSISTENT_TEXT_PLAIN
		// 当rabbitMQ暂时down掉，下次重启之后，工作者还是能接受目前发送的消息。
		channel.basicPublish("", QUEUE_NAME,
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

		System.out.println("Send message : " + message);
		channel.close();
		connection.close();
	}
}
