package com.sung.rabbitmq.route.direct;

import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//日志类型
		String[] msgType = new String[]{"info","warning","error"};
		String routingKey = getRoutingKey(msgType);
		//测试信息
		String[] msg = new String[]{"xuz RabbitMQ Routing Test!","very Good!","This is a Info"};
		String message = getMessage(msg);
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
		System.out.println("send:["+routingKey+":"+message+"]");
		channel.close();
		conn.close();
	}

	private static String getMessage(String[] strings) {
		if(strings.length<2){
			return "Hello World!";
		}
		return joinStrings(strings,"",1);
	}

	private static String joinStrings(String[] strings, String string,int startIndex) {
		int length = strings.length;
	    if (length == 0 ) return "";
	    if (length < startIndex ) return "";
	    StringBuilder words = new StringBuilder(strings[startIndex]);
	    for (int i = startIndex + 1; i < length; i++) {
	        words.append(string).append(strings[i]);
	    }
	    return words.toString();
	}

	private static String getRoutingKey(String[] strings) {
		Random random = new Random();
		int ranVal = random.nextInt(3);
		return strings[ranVal];
	}
}
