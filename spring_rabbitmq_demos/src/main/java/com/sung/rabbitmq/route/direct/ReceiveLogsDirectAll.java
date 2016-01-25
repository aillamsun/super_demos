package com.sung.rabbitmq.route.direct;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
/**
 * 接收所有类型的消息
 * @author Administrator
 *
 */
public class ReceiveLogsDirectAll {
	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
	    String queueName = channel.queueDeclare().getQueue();
	    String[] msgType = new String[]{"info","warning","error"};
	    if (msgType.length < 1){
	      System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
	      System.exit(1);
	    }
	    /**
	     * 绑定多种类型包括：info、warning、error
	     */
	    for(String routingKey : msgType){    
	      channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
	    }
	    
	    System.out.println(" ReceiveLogsDirectAll---->Waiting for messages. To exit press CTRL+C");
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queueName, true, consumer);
	    while (true) {
	    	System.out.println(111);
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      String message = new String(delivery.getBody());
	      String routingKey = delivery.getEnvelope().getRoutingKey();
	      System.out.println("ReceiveLogsDirectAll----->Received '" + routingKey + "':'" + message + "'");   
	    }
	}
}
