package com.sung.rabbitmq.rpc;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RPCServer {
	private static final String RPC_QUEUE_NAME = "rpc_queue";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

		System.out.println("Awaiting RPC requests");

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			BasicProperties props = delivery.getProperties();
			BasicProperties replyProps = new BasicProperties.Builder().correlationId(props.getCorrelationId()).build();
			String message = new String(delivery.getBody());
			int n = Integer.parseInt(message);
			System.out.println("Resv : " + message);
			channel.basicPublish("", props.getReplyTo(), replyProps,"OOK".getBytes());
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}
