package com.sung.rpc.client;

import com.sung.rpc.annotation.Codecs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;


public abstract class AbstractClientInvocationHandler implements InvocationHandler {

	private List<InetSocketAddress> servers;
	
	private int clientNums;
	
	private int connectTimeout;
	
	private String targetInstanceName;
	
	private Codecs codecType;
	
	// per method timeout,some special method use methodName.toLowerCase to set timeout,other use *
	private Map<String, Integer> methodTimeouts;
	
	public AbstractClientInvocationHandler(List<InetSocketAddress> servers,int clientNums,int connectTimeout,
										   String targetInstanceName,Map<String, Integer> methodTimeouts,
										   Codecs codecType){
		this.servers = Collections.unmodifiableList(servers);
		this.clientNums = clientNums;
		this.connectTimeout = connectTimeout;
		this.methodTimeouts = methodTimeouts;
		this.targetInstanceName = targetInstanceName;
		this.codecType = codecType;
	}
	
	public void updateServers(List<InetSocketAddress> servers){
		this.servers = Collections.unmodifiableList(servers);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		InetSocketAddress server = null;
		if(servers.size() == 1){
			server = servers.get(0);
		}
		else{
			// random is not thread-safe,so...
			Random random = new Random();
			server = servers.get(random.nextInt(servers.size()));
		}
		Client client = getClientFactory().get(server.getAddress().getHostAddress(), server.getPort(), connectTimeout, clientNums);
		String methodName = method.getName();
		int timeout = 0;
		if(methodTimeouts.containsKey(methodName.toLowerCase())){
			timeout = methodTimeouts.get(methodName);
		}
		else{
			timeout = methodTimeouts.get("*");
		}
		return client.invokeSync(targetInstanceName, methodName, method.getParameterTypes(), args, timeout, codecType);
	}
	
	public abstract ClientFactory getClientFactory();

}
