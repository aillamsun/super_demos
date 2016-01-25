package com.sung.rabbitmq.rpc;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.sung.rabbitmq.utils.AMQPClientConstant;
import com.sung.rabbitmq.utils.AMQPClientHelper;
import com.sung.rabbitmq.utils.MapBasedAnyObject;

public class RPCClient {
	
	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	public RPCClient() throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put(AMQPClientConstant.AMQP_HOST, "localhost");
		AMQPClientHelper helper = new AMQPClientHelper(new MapBasedAnyObject(map));
		connection = helper.newConnection(helper.createConnectionFactory());
		channel = helper.createChannel(connection);
		
		replyQueueName = helper.getQueue(channel);
		consumer = helper.createQueueingConsumer(channel);
		helper.basicConsume(channel, replyQueueName, true, consumer);
	}
	
	
	public String call(String message) throws Exception {     
	    String response = null;
	    String corrId = java.util.UUID.randomUUID().toString();

	    BasicProperties props = new BasicProperties .Builder().correlationId(corrId).replyTo(replyQueueName) .build();

	    channel.basicPublish("", requestQueueName, props, message.getBytes());
	    while (true) {
	        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	        if (delivery.getProperties().getCorrelationId().equals(corrId)) {
	            response = new String(delivery.getBody());
	            break;
	        }
	    }

	    return response; 
	}
	public void close() throws Exception {
	    connection.close();
	}
}
