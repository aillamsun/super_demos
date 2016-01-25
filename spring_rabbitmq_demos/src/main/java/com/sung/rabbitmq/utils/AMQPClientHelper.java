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

public class AMQPClientHelper {

	// private AMQPClientHelper(){}

	private AnyObject conf;

	private static AMQPClientHelper INSTANCE = null;

	public static AMQPClientHelper getInstance() {
		synchronized (AMQPClientHelper.class) {
			if (INSTANCE != null) {
				return INSTANCE;
			} else {
				return null;// INSTANCE = new AMQPClientHelper();
			}
		}
	}

	public AMQPClientHelper(AnyObject conf) {
		this.conf = conf;
	}

	public ConnectionFactory createConnectionFactory() throws Exception {
		return this.conf.createConnectionFactory();
	}

	public Connection newConnection(ConnectionFactory factory) throws Exception {
		return this.conf.newConnection(factory);
	}

	public Channel createChannel(Connection conn) throws Exception {
		return this.conf.createChannel(conn);
	}

	public String getRoutingKey() {
		return this.conf.getRoutingKey();
	}

	public String getExchangeType() {
		return this.conf.getExchangeType();
	}

	public String getQueueName() {
		return this.conf.getQueueName();
	}

	public String getExchangeName() {
		return this.conf.getExchangeName();
	}

	public String getQueue(Channel channel) throws Exception {
		return this.conf.getQueue(channel);
	}

	/**
	 * @Title: queueDeclare
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:44:49
	 * @Description: 定义一个queue，由server去命名
	 * @param：@param channel
	 * @param：@return
	 * @param：@throws Exception
	 * @return：AMQP.Queue.DeclareOk返回类型
	 * @throws
	 */
	public AMQP.Queue.DeclareOk queueDeclare(Channel channel) throws Exception {
		return this.conf.queueDeclare(channel);
	}

	/**
	 * @Title: queueDeclare
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:45:22
	 * @Description: 定义一个queue
	 * @param：@param channel
	 * @param：@param queueName 名称
	 * @param：@param durable durable 是否持续存在
	 * @param：@param exclusive true if we are declaring an exclusive queue
	 * @param：@param autoDelete 是否自动删除，true：不在使用了server将会自动删除它
	 * @param：@param arguments other properties (construction arguments) for the
	 *               queue
	 * @param：@return
	 * @param：@throws Exception
	 * @return：AMQP.Queue.DeclareOk返回类型
	 * @throws
	 */
	public AMQP.Queue.DeclareOk queueDeclare(Channel channel, String queueName,
			boolean durable, boolean exclusive, boolean autoDelete,
			Map<String, Object> arguments) throws Exception {
		return this.conf.queueDeclare(channel, queueName, durable, exclusive,
				autoDelete, arguments);
	}

	/**
	 * @Title: queueDelete
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:46:34
	 * @Description: 删除queue，不管它是否在使用
	 * @param：@param channel
	 * @param：@param queueName
	 * @param：@return
	 * @param：@throws IOException
	 * @return：AMQP.Queue.DeleteOk返回类型
	 * @throws
	 */
	public AMQP.Queue.DeleteOk queueDelete(Channel channel, String queueName)
			throws IOException {
		return this.conf.queueDelete(channel, queueName);
	}

	/**
	 * @Title: queueDelete
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:46:49
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param：@param channel
	 * @param：@param queueName
	 * @param：@param ifUnused 是否只删除没有被使用的queue
	 * @param：@param ifEmpty 是否只删除消息是空的queue
	 * @param：@return
	 * @param：@throws IOException
	 * @return：AMQP.Queue.DeleteOk返回类型
	 * @throws
	 */
	public AMQP.Queue.DeleteOk queueDelete(Channel channel, String queueName,
			boolean ifUnused, boolean ifEmpty) throws IOException {
		return this.conf.queueDelete(channel, queueName, ifUnused, ifEmpty);
	}

	public AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
			String exchangeName, String type) throws Exception {
		return this.conf.exchangeDeclare(channel, exchangeName, type);
	}

	/**
	 * @Title: exchangeDeclare
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:40:43
	 * @Description: 定义exchange，non-autodelete
	 * @param：@param channel
	 * @param：@param exchangeName exchange名称
	 * @param：@param type exchange类型
	 * @param：@param durable 是否持续存在(持续存在，即使server重启也会存在)
	 * @param：@throws Exception
	 * @return：AMQP.Exchange.DeclareOk返回类型
	 * @throws
	 */
	public AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
			String exchangeName, String type, boolean durable) throws Exception {
		return this.conf.exchangeDeclare(channel, exchangeName, type, durable);
	}

	/**
	 * @Title: exchangeDeclare
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:42:57
	 * @Description: 定义exchange
	 * @param：@param channel
	 * @param：@param exchangeName exchange名称
	 * @param：@param type exchange类型
	 * @param：@param durable 是否持续存在(持续存在，即使server重启也会存在)
	 * @param：@param autoDelete 是否自动删除，自动删除-server会在它不在使用的时候将其删除
	 * @param：@param arguments other properties (construction arguments) for the
	 *               exchange
	 * @param：@throws Exception
	 * @return：AMQP.Exchange.DeclareOk返回类型
	 * @throws
	 */
	public AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
			String exchangeName, String type, boolean durable,
			boolean autoDelete, Map<String, Object> arguments) throws Exception {
		return this.conf.exchangeDeclare(channel, exchangeName, type, durable,
				autoDelete, arguments);
	}

	public AMQP.Exchange.DeclareOk exchangeDeclare(Channel channel,
			String exchangeName, String type, boolean durable,
			boolean autoDelete, boolean internal, Map<String, Object> arguments)
			throws Exception {
		return this.conf.exchangeDeclare(channel, exchangeName, type, durable,
				autoDelete, internal, arguments);
	}

	/**
	 * @Title: queueBind
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:47:19
	 * @Description: 使用routingKey将queue绑定到exchange上
	 * @param：@param channel
	 * @param：@param queueName 队列名
	 * @param：@param exchangeName 交换名称
	 * @param：@param routingKey 路由KEY
	 * @param：@throws Exception
	 * @return：AMQP.Queue.BindOk返回类型
	 * @throws
	 */
	public AMQP.Queue.BindOk queueBind(Channel channel, String queueName,
			String exchangeName, String routingKey) throws Exception {
		return this.conf
				.queueBind(channel, queueName, exchangeName, routingKey);
	}

	/**
	 * @Title: queueBind
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:48:20
	 * @Description: 使用routingKey将queue绑定到exchange上，带参数
	 * @param：@param channel
	 * @param：@param queueName
	 * @param：@param exchangeName
	 * @param：@param routingKey
	 * @param：@param arguments
	 * @param：@return
	 * @param：@throws Exception
	 * @return：AMQP.Queue.BindOk返回类型
	 * @throws
	 */
	public AMQP.Queue.BindOk queueBind(Channel channel, String queueName,
			String exchangeName, String routingKey,
			Map<String, Object> arguments) throws Exception {
		return this.conf.queueBind(channel, queueName, exchangeName,
				routingKey, arguments);
	}

	/**
	 * @Title: queueUnbind
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:52:45
	 * @Description: 解除绑定
	 * @param：@param channel
	 * @param：@param queueName
	 * @param：@param exchangeName
	 * @param：@param routingKey
	 * @param：@return
	 * @param：@throws IOException
	 * @return：AMQP.Queue.UnbindOk返回类型
	 * @throws
	 */
	public AMQP.Queue.UnbindOk queueUnbind(Channel channel, String queueName,
			String exchangeName, String routingKey) throws IOException {
		return queueUnbind(channel, queueName, exchangeName, routingKey, null);
	}

	/**
	 * @Title: queueUnbind
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:53:00
	 * @Description: 解除绑定，带参数
	 * @param：@param channel
	 * @param：@param queueName
	 * @param：@param exchangeName
	 * @param：@param routingKey
	 * @param：@param arguments
	 * @param：@return
	 * @param：@throws IOException
	 * @return：AMQP.Queue.UnbindOk返回类型
	 * @throws
	 */
	public AMQP.Queue.UnbindOk queueUnbind(Channel channel, String queueName,
			String exchangeName, String routingKey,
			Map<String, Object> arguments) throws IOException {
		return this.conf.queueUnbind(channel, queueName, exchangeName,
				routingKey, arguments);
	}

	public QueueingConsumer createQueueingConsumer(Channel channel)
			throws Exception {
		return this.conf.createQueueingConsumer(channel);
	}

	public String basicConsume(Channel channel, String queueName,
			Consumer callback) throws IOException {
		return this.basicConsume(channel, queueName, callback);
	}

	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, Consumer callback) throws IOException {
		return this.conf.basicConsume(channel, queueName, autoAck, callback);
	}

	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, String arguments, Consumer callback)
			throws IOException {
		return this.conf.basicConsume(channel, queueName, autoAck, arguments,
				callback);
	}

	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, Map<String, Object> arguments, Consumer callback)
			throws IOException {
		return this.conf.basicConsume(channel, queueName, autoAck, arguments,
				callback);
	}

	public String basicConsume(Channel channel, String queueName,
			boolean autoAck, String consumerTag, boolean noLocal,
			boolean exclusive, Map<String, Object> arguments, Consumer callback)
			throws IOException {
		return this.conf.basicConsume(channel, queueName, autoAck, consumerTag,
				noLocal, exclusive, arguments, callback);
	}

	public byte[] getBodyAndAck(QueueingConsumer.Delivery delivery)
			throws Exception {
		return this.conf.getBodyAndAck(delivery);
	}

	public void basicPublish(Channel channel, String exchangeName,
			String routingKey, AMQP.BasicProperties props, byte[] body)
			throws Exception {
		this.conf.basicPublish(channel, exchangeName, routingKey, props, body);
	}

	public void basicPublish(Channel channel, String exchangeName,
			String routingKey, boolean mandatory, AMQP.BasicProperties props,
			byte[] body) throws Exception {
		this.conf.basicPublish(channel, exchangeName, routingKey, mandatory,
				props, body);
	}

	/**
	 * @Title: basicPublish
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:39:17
	 * @Description: 发送消息
	 * @param：@param channel
	 * @param：@param exchangeName exchange名称
	 * @param：@param routingKey routingKey名称
	 * @param：@param mandatory mandatory 是否强制发送
	 * @param：@param immediate immediate 是否立即发送
	 * @param：@param props props other properties for the message - routing
	 *               headers etc
	 * @param：@param body 消息
	 * @param：@throws Exception
	 * @return：void返回类型
	 * @throws
	 */
	public void basicPublish(Channel channel, String exchangeName,
			String routingKey, boolean mandatory, boolean immediate,
			AMQP.BasicProperties props, byte[] body) throws Exception {
		this.conf.basicPublish(channel, exchangeName, routingKey, mandatory,
				immediate, props, body);
	}

	/**
	 * @Title: basicGet
	 * @author：孙刚
	 * @date：2014年8月22日 上午9:57:03
	 * @Description: 指定从某个队列获取消息
	 * @param：@param channel
	 * @param：@param queueName
	 * @param：@param autoAck
	 * @param：@throws IOException
	 * @return：GetResponse返回类型
	 * @throws
	 */
	public GetResponse basicGet(Channel channel, String queueName,
			boolean autoAck) throws IOException {
		return this.conf.basicGet(channel, queueName, autoAck);
	}
}
