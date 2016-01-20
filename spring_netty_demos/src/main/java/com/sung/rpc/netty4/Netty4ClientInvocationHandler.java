package com.sung.rpc.netty4;
import com.sung.rpc.annotation.Codecs;
import com.sung.rpc.client.AbstractClientInvocationHandler;
import com.sung.rpc.client.ClientFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;


public class Netty4ClientInvocationHandler extends
		AbstractClientInvocationHandler {

	public Netty4ClientInvocationHandler(List<InetSocketAddress> servers,
			int clientNums, int connectTimeout, String targetInstanceName,
			Map<String, Integer> methodTimeouts, Codecs codectype) {
		super(servers, clientNums, connectTimeout, targetInstanceName,
				methodTimeouts, codectype);
	}

	public ClientFactory getClientFactory() {
		return Netty4ClientFactory.getInstance();
	}

}
