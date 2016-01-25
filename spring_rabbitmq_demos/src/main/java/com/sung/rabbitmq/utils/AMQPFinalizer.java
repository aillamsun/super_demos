package com.sung.rabbitmq.utils;

import java.io.IOException;
import java.util.Set;

import com.google.common.collect.Sets;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class AMQPFinalizer {

	private final Set<Connection> connections = Sets.newHashSet();
	private final Set<Channel> channels = Sets.newHashSet();

	public void registerConnection(Connection conn) {
		this.connections.add(conn);
	}

	public synchronized void registerChannel(Channel channel) {
		channels.add(channel);
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeChannel(Channel channel) {
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeChannelAndConnection(Channel channel,Connection connection){
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
