package com.sung.rabbitmq.utils;

/**
* @ClassName: AMQPClientConstant 
* @Description: 存放一些RabbitMQ 客户端使用的一些自定义命名
* @author 孙刚
* @date 2014年8月19日 上午11:14:42 
* 
* @修改人 xxx
* @修改描述 xxx
* @修改时间 xxxx-xx-xx
 */
public class AMQPClientConstant {
	
	/**
	 * 链接用户名
	 */
	public static String AMQP_USERNAME = "username";
	/**
	 * 链接用户密码
	 */
	public static String AMQP_PASSWORD = "password";
	/**
	 * 链接的 虚拟机名
	 */
	public static String AMQP_VIRTUAL_HOST = "virtual_host";
	/**
	 * host地址
	 */
	public static String AMQP_HOST = "host";
	/**
	 * 端口号
	 */
	public static String AMQP_PORT = "port";
	/**
	 * 
	 */
	public static String AMQP_REQUESTHEARTBEAT = "requestheartbeat";
	/**
	 * 
	 */
	public static String AMQP_CONSUMER_CANCEL_NOTIFY = "consumer_cancel_notify";
	/**
	 * 
	 */
	public static String CONSUMER_CANCEL_NOTIFY = "cancel_notify";
	/**
	 * 
	 */
	public static String CHANNL_BASIC_QOS = "channel_basic_qos";
	
	/**
	 * 路由 KEY
	 */
	public static String QUEUE_ROUTING_KEY = "routing_key";
	/**
	 * 交换类型
	 */
	public static String EXCHANGE_TYPE = "exchange_type";
	/**
	 * 队列名称
	 */
	public static String QUEUE_NAME = "queue_name";
	/**
	 * 交换名称
	 */
	public static String EXCHANGE_NAME = "exchange_name";
	/**
	 * 
	 */
	public static String X_HA_POLICY = "x_ha_policy";
	/**
	 * 
	 */
	public static String X_HA_PARAMS = "x_ha_params";
	/**
	 * 
	 */
	public static String AMQP_HOSTS = "hosts";
}
