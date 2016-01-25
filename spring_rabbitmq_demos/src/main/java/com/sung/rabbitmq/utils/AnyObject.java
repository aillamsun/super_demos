package com.sung.rabbitmq.utils;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;

public interface AnyObject {

	Double getDouble(String key);

	Float getFloat(String key);

	Long getLong(String key);

	Integer getInteger(String key);

	String getString(String key);

	Boolean getBoolean(String key);

	<T> Iterable<T> getIterable(String key);

	ConnectionFactory createConnectionFactory() throws Exception;

	Connection newConnection(ConnectionFactory factory) throws Exception;

	Channel createChannel(Connection conn) throws Exception;

	String getRoutingKey();

	String getExchangeType();

	String getQueueName();

	String getExchangeName();

	String getQueue(Channel channel) throws Exception;

	AMQP.Queue.DeclareOk queueDeclare(Channel channel) throws Exception;

	AMQP.Queue.DeclareOk queueDeclare(Channel channel, String queueName,
									  boolean durable, boolean exclusive, boolean autoDelete,
									  Map<String, Object> arguments) throws Exception;

	AMQP.Queue.DeleteOk queueDelete(Channel channel, String queueName)
			throws IOException;

	AMQP.Queue.DeleteOk queueDelete(Channel channel, String queueName,
									boolean ifUnused, boolean ifEmpty) throws IOException;

	AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
											String exchangeName, String type) throws Exception;

	AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
											String exchangeName, String type, boolean durable) throws Exception;

	AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
											String exchangeName, String type, boolean durable,
											boolean autoDelete, Map<String, Object> arguments) throws Exception;

	AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
											String exchangeName, String type, boolean durable,
											boolean autoDelete, boolean internal, Map<String, Object> arguments)
			throws Exception;

	AMQP.Queue.BindOk queueBind(Channel channel, String queueName,
								String exchangeName, String routingKey) throws Exception;

	AMQP.Queue.BindOk queueBind(Channel channel, String queueName,
								String exchangeName, String routingKey,
								Map<String, Object> arguments) throws Exception;

	AMQP.Queue.UnbindOk queueUnbind(Channel channel, String queueName, String exchangeName,
									String routingKey) throws IOException;

	AMQP.Queue.UnbindOk queueUnbind(Channel channel, String queueName, String exchangeName,
									String routingKey, Map<String, Object> arguments)
			throws IOException;

	QueueingConsumer createQueueingConsumer(Channel channel) throws Exception;

	String basicConsume(Channel channel, String queueName, Consumer callback)
			throws IOException;

	String basicConsume(Channel channel, String queueName, boolean autoAck,
						Consumer callback) throws IOException;

	String basicConsume(Channel channel, String queueName, boolean autoAck,
						String consumerTag, Consumer callback) throws IOException;

	String basicConsume(Channel channel, String queueName, boolean autoAck,
						Map<String, Object> arguments, Consumer callback)
			throws IOException;

	String basicConsume(Channel channel, String queueName, boolean autoAck,
						String consumerTag, boolean noLocal, boolean exclusive,
						Map<String, Object> arguments, Consumer callback)
			throws IOException;

	byte[] getBodyAndAck(QueueingConsumer.Delivery delivery) throws Exception;

	void basicPublish(Channel channel, String exchangeName, String routingKey,
					  AMQP.BasicProperties props, byte[] body) throws Exception;

	void basicPublish(Channel channel, String exchangeName, String routingKey,
					  boolean mandatory, AMQP.BasicProperties props, byte[] body)
			throws Exception;

	void basicPublish(Channel channel, String exchangeName, String routingKey,
					  boolean mandatory, boolean immediate, AMQP.BasicProperties props,
					  byte[] body) throws Exception;
	
	GetResponse basicGet(Channel channel, String queueName, boolean autoAck) throws IOException;
}
