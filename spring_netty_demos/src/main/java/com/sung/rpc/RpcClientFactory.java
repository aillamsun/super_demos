package com.sung.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.sung.rpc.annotation.Codecs;
import com.sung.rpc.annotation.RpcMethod;
import com.sung.rpc.annotation.RpcService;
import com.sung.rpc.client.Client;
import com.sung.rpc.netty4.Netty4ClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Create on @2013-8-20 @下午2:39:50 
 * @author bsli@ustcinfo.com
 */
public class RpcClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientFactory.class);
	
	/** 
     * 引用服务, 连接超时时间 1000ms
     *  
     * @param <T> 接口泛型 
     * @param interfaceClass 接口类型 
     * @param host 服务器主机名 
     * @param port 服务器端口
     //* @param timeout 连接超时时间，单位毫秒
     * @return 远程服务 
     * @throws RpcException 
     */  
    public static <T> T refer(Class<T> interfaceClass, String host, int port) throws RpcException {  
        return refer(interfaceClass, host, port, 1000); 
    }  

	/** 
     * 引用服务 
     *  
     * @param <T> 接口泛型 
     * @param interfaceClass 接口类型 
     * @param host 服务器主机名 
     * @param port 服务器端口
     * @param timeout 连接超时时间，单位毫秒 
     * @return 远程服务 
     * @throws RpcException 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T refer(Class<T> interfaceClass, String host, int port, int timeout) throws RpcException {  
        if (interfaceClass == null)  
            throw new IllegalArgumentException("Interface class == null");  
        if (! interfaceClass.isInterface())  
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");  
        if (host == null || host.length() == 0)  
            throw new IllegalArgumentException("Host == null!");  
        if (port <= 0 || port > 65535)  
            throw new IllegalArgumentException("Invalid port " + port);  
        if (timeout < 1000)
        	timeout = 1000;
        	
        LOGGER.info("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);  
        
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new JDKInvocationHandler(interfaceClass, host, port, timeout));  
    }  
    
    private static class JDKInvocationHandler implements InvocationHandler {
    	private Class<?> interfaceClass;
    	
    	private Client client;
    	
    	private int timeout = 1000;
    	
    	private Codecs encoder = Codecs.JAVA_CODEC;
    	
		public JDKInvocationHandler(Class<?> interfaceClass, String host, int port, int timeout) {
			this.interfaceClass = interfaceClass;
			RpcService rpcService = interfaceClass.getAnnotation(RpcService.class);
			
			if(rpcService != null) {
				if(rpcService.timeout() > 1000)
					timeout = rpcService.timeout();
				encoder = rpcService.encoder();
			}
			
			try {
				client = Netty4ClientFactory.getInstance().get(host, port, timeout);
			} catch (Exception e) {
				throw new RpcException(e.getMessage(), e);
			}
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
				throws Throwable {
			RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
			
			if(rpcMethod != null) {
				if(rpcMethod.timeout() > 1000)
					timeout = rpcMethod.timeout();
			}
			
			Object result = client.invokeSync(interfaceClass.getName(), method.getName(), method.getParameterTypes(), arguments, timeout, encoder);
			return result;
		}
    	
    }
}
