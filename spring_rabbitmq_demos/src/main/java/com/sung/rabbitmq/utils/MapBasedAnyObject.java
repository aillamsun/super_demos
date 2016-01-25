package com.sung.rabbitmq.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.BindOk;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.AMQP.Queue.DeleteOk;
import com.rabbitmq.client.AMQP.Queue.UnbindOk;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class MapBasedAnyObject implements AnyObject {

	private final Map<String, Object> map;

	public MapBasedAnyObject(Map<String, Object> map) {
		this.map = map;
	}

	public Object get(String key) {
		return get(this.map, key);
	}

	private Object get(Map<String, Object> map, String key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConnectionFactory createConnectionFactory() throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		// 设置用户名
		String username = getString(AMQPClientConstant.AMQP_USERNAME);
		if (username != null & !"".equals(username)) {
			factory.setUsername(username);
		}
		// 设置密码
		String password = getString(AMQPClientConstant.AMQP_PASSWORD);
		if (password != null && !"".equals(password)) {
			factory.setPassword(password);
		}
		// 设置 virtualHost
		String virtualHost = getString(AMQPClientConstant.AMQP_VIRTUAL_HOST);
		if (virtualHost != null && !"".equals(virtualHost)) {
			factory.setVirtualHost(virtualHost);
		}
		// 设置 host
		String host = getString(AMQPClientConstant.AMQP_HOST);
		if (host != null && !"".equals(host)) {
			factory.setHost(host);
		}
		// 设置 port
		Integer port = getInteger(AMQPClientConstant.AMQP_PORT);
		if (port != null) {
			factory.setPort(port);
		}
		// 设置 requestedHeartbeat
		Integer requestedHeartbeat = getInteger(AMQPClientConstant.AMQP_REQUESTHEARTBEAT);
		if (requestedHeartbeat != null) {
			factory.setRequestedHeartbeat(requestedHeartbeat);
		}

		Boolean notifyCancel = getBoolean(AMQPClientConstant.AMQP_CONSUMER_CANCEL_NOTIFY);
		if (notifyCancel != null && (notifyCancel.booleanValue())) {
			Map properties = new HashMap();
			Map capabilities = new HashMap();
			capabilities.put(AMQPClientConstant.CONSUMER_CANCEL_NOTIFY,
					Boolean.valueOf(true));
			properties.put("capabilities", capabilities);
			factory.setClientProperties(properties);
		}
		return factory;
	}

	@Override
	public Connection newConnection(ConnectionFactory factory) throws Exception {
		Address[] hosts = getMultipleHosts();
		Connection conn;
		if (hosts == null) {
			conn = factory.newConnection();
		} else {
			conn = factory.newConnection(hosts);
		}
		return conn;
	}

	@Override
	public Channel createChannel(Connection conn) throws Exception {
		if (conn == null) {
			throw new NullPointerException("connection is not null");
		}
		return conn.createChannel();
	}

	@Override
	public DeclareOk queueDeclare(Channel channel) throws Exception {
		return queueDeclare(channel, "", false, true, true, null);
	}

	@Override
	public DeclareOk queueDeclare(Channel channel, String queueName,
			boolean durable, boolean exclusive, boolean autoDelete,
			Map<String, Object> arguments) throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (queueName == null || "".equals(queueName)) {
			throw new NullPointerException("queue name is not null");
		}
		return channel.queueDeclare(queueName, durable, exclusive, autoDelete,
				arguments);
	}

	@Override
	public DeleteOk queueDelete(Channel channel, String queueName)
			throws IOException {
		return queueDelete(channel, queueName, false, false);
	}

	@Override
	public DeleteOk queueDelete(Channel channel, String queueName,
			boolean ifUnused, boolean ifEmpty) throws IOException {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (queueName == null || "".equals(queueName)) {
			throw new NullPointerException("queue name is not null");
		}
		return channel.queueDelete(queueName, ifUnused, ifEmpty);
	}

	@Override
	public com.rabbitmq.client.AMQP.Exchange.DeclareOk exchangeDeclare(
			Channel channel, String exchangeName, String type) throws Exception {
		return exchangeDeclare(channel, exchangeName, type, false, false, null);
	}

	@Override
	public com.rabbitmq.client.AMQP.Exchange.DeclareOk exchangeDeclare(
			Channel channel, String exchangeName, String type, boolean durable)
			throws Exception {
		return exchangeDeclare(channel, exchangeName, type, durable, false,
				null);
	}

	@Override
	public com.rabbitmq.client.AMQP.Exchange.DeclareOk exchangeDeclare(
			Channel channel, String exchangeName, String type, boolean durable,
			boolean autoDelete, Map<String, Object> arguments) throws Exception {
		return exchangeDeclare(channel, exchangeName, type, durable,
				autoDelete, false, arguments);
	}

	@Override
	public com.rabbitmq.client.AMQP.Exchange.DeclareOk exchangeDeclare(
			Channel channel, String exchangeName, String type, boolean durable,
			boolean autoDelete, boolean internal, Map<String, Object> arguments)
			throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (exchangeName == null || "".equals(exchangeName)) {
			throw new NullPointerException("exchangeName is not null");
		}
		if (type == null || "".equals(type)) {
			throw new NullPointerException("exchange type is not null");
		}
		return channel.exchangeDeclare(exchangeName, type, durable, autoDelete,
				internal, arguments);
	}

	@Override
	public BindOk queueBind(Channel channel, String queueName,
			String exchangeName, String routingKey) throws Exception {
		return queueBind(channel, queueName, exchangeName, routingKey, null);
	}

	@Override
	public BindOk queueBind(Channel channel, String queueName,
			String exchangeName, String routingKey,
			Map<String, Object> arguments) throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (queueName == null || "".equals(queueName)) {
			throw new NullPointerException("queue name is not null");
		}
		if (exchangeName == null || "".equals(exchangeName)) {
			throw new NullPointerException("exchange Name name is not null");
		}
		return channel
				.queueBind(queueName, exchangeName, routingKey, arguments);
	}

	@Override
	public UnbindOk queueUnbind(Channel channel, String queueName,
			String exchangeName, String routingKey) throws IOException {
		return queueUnbind(channel, queueName, exchangeName, routingKey, null);
	}

	@Override
	public UnbindOk queueUnbind(Channel channel, String queueName,
			String exchangeName, String routingKey,
			Map<String, Object> arguments) throws IOException {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (queueName == null || "".equals(queueName)) {
			throw new NullPointerException("queue name is not null");
		}
		if (exchangeName == null || "".equals(exchangeName)) {
			throw new NullPointerException("exchange Name name is not null");
		}
		return channel.queueUnbind(queueName, exchangeName, routingKey, arguments);
	}

	@Override
	public String basicConsume(Channel channel, String queueName,
			Consumer callback) throws IOException {
		return basicConsume(channel, queueName, false, callback);
	}

	@Override
	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, Consumer callback) throws IOException {
		return basicConsume(channel, queueName, autoAck, "", callback);
	}

	@Override
	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, String consumerTag, Consumer callback)
			throws IOException {
		return basicConsume(channel, queueName, autoAck, consumerTag, false,
				false, null, callback);
	}

	@Override
	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, Map<String, Object> arguments, Consumer callback)
			throws IOException {
		return basicConsume(channel, queueName, autoAck, "", false, false,
				arguments, callback);
	}

	@Override
	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, String consumerTag, boolean noLocal,
			boolean exclusive, Map<String, Object> arguments, Consumer callback)
			throws IOException {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (queueName == null || "".equals(queueName)) {
			throw new NullPointerException("queue name is not null");
		}
		return channel.basicConsume(queueName, autoAck, consumerTag, noLocal,
				exclusive, arguments, callback);
	}

	@Override
	public QueueingConsumer createQueueingConsumer(Channel channel)
			throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		QueueingConsumer consumer = new QueueingConsumer(channel);
		return consumer;
	}

	@Override
	public byte[] getBodyAndAck(Delivery delivery) throws Exception {
		if (delivery == null) {
			throw new NullPointerException("Delivery is not null");
		}
		byte[] body = delivery.getBody();
		return body;
	}

	@Override
	public void basicPublish(Channel channel, String exchangeName,
			String routingKey, BasicProperties props, byte[] body)
			throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		channel.basicPublish(exchangeName, routingKey, props, body);
	}

	@Override
	public void basicPublish(Channel channel, String exchangeName,
			String routingKey, boolean mandatory, BasicProperties props,
			byte[] body) throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		channel.basicPublish(exchangeName, routingKey, mandatory, props, body);
	}

	@Override
	public void basicPublish(Channel channel, String exchangeName,
			String routingKey, boolean mandatory, boolean immediate,
			BasicProperties props, byte[] body) throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		channel.basicPublish(exchangeName, routingKey, mandatory, immediate,
				props, body);
	}
	
	
	
	@Override
	public String getQueue(Channel channel) throws Exception {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		return channel.queueDeclare().getQueue();
	}

	@Override
	public String getRoutingKey() {
		return getString(AMQPClientConstant.QUEUE_ROUTING_KEY);
	}

	@Override
	public String getExchangeType() {
		return getString(AMQPClientConstant.EXCHANGE_TYPE);
	}

	@Override
	public String getQueueName() {
		return getString(AMQPClientConstant.QUEUE_NAME);
	}

	@Override
	public String getExchangeName() {
		return getString(AMQPClientConstant.EXCHANGE_NAME);
	}

	@Override
	public <T> Iterable<T> getIterable(String key) {
		Iterable iterable = (Iterable) get(key);
		return iterable == null ? null : new AnyIterable(iterable);
	}

	@Override
	public Double getDouble(String key) {
		return (Double) get(key);
	}

	@Override
	public Float getFloat(String key) {
		return (Float) get(key);
	}

	@Override
	public Long getLong(String key) {
		return (Long) get(key);
	}

	@Override
	public Integer getInteger(String key) {
		return (Integer) get(key);
	}

	@Override
	public String getString(String key) {
		return (String) get(key);
	}

	@Override
	public Boolean getBoolean(String key) {
		return (Boolean) get(key);
	}

	private Address[] getMultipleHosts() {
		Iterable<String> hosts = getIterable(AMQPClientConstant.AMQP_HOSTS);
		if (hosts == null) {
			return null;
		}
		List<Address> addressList = new ArrayList<Address>();
		for (String host : hosts) {
			if (host != null) {
				addressList.add(new Address(host));
			}
		}
		if (addressList.size() > 0) {
			return (Address[]) addressList.toArray(new Address[0]);
		}
		return null;
	}

	private static final class AnyIterable implements Iterable {
		private final Iterable iterable;

		AnyIterable(Iterable iterable) {
			this.iterable = iterable;
		}

		public Iterator iterator() {
			return new AnyIterator(this.iterable.iterator());
		}
	}

	private static final class AnyIterator implements Iterator {
		private final Iterator iterator;

		AnyIterator(Iterator iterator) {
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		public Object next() {
			Object o = this.iterator.next();
			if ((o instanceof Iterable)) {
				return new AnyIterable((Iterable) o);
			}
			if ((o instanceof Map)) {
				return new MapBasedAnyObject((Map) o);
			}
			return o;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public GetResponse basicGet(Channel channel, String queueName,
			boolean autoAck) throws IOException {
		if (channel == null) {
			throw new NullPointerException("Channel is not null");
		}
		if (queueName == null || "".equals(queueName)) {
			throw new NullPointerException("queue name is not null");
		}
		return channel.basicGet(queueName, autoAck);
	}
}
