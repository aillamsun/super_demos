package com.sung.rabbitmq.one2one.example02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class WorkerTwo {
	private static final String TASK_QUEUE_NAME = "task_queue";
	private static final boolean autoAck = false;
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		/**
	     * true:
	     * 这样设置之后，服务器收到消息后就会立刻将消息写入到硬盘，就可以防止突然服务器挂掉，而引起数据丢失了，但是服务器如果刚收到消息，还没有来得写入硬盘，就挂掉了，这样
	     * 无法避免消息得丢失。
	     */
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		//使用了channel.basicQos(1)
		//保证在接收端一个消息没有处理完时不会接收另一个消息，
		//即接收端发送了ack后才会接收下一个消息。
		//在这种情况下发送端会尝试把消息发送给下一个not busy的接收端。
		channel.basicQos(1);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//在使用channel.basicConsume接收消息时使autoAck为false，
		//即不自动会发ack，由channel.basicAck()在消息处理完成后发送消息。
		 /**
	     * false：设置确认消息，true表示接收到消息之后，将返回给服务端确定消息
	     */
		channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			System.out.println(" [x] Received '" + message + "'");
			doWork(message);
			System.out.println(" [x] Done");
			//设置消息确认机制，如将如下代码注释掉，则
			//一旦将autoAck关闭之后，一定要记得处理完消息之后，向服务器确认消息。
			//否则服务器将会一直转发该消息
			//channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}
}
