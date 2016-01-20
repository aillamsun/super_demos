package com.sung.rpc.client;


import com.sung.rpc.ResponseWrapper;
import com.sung.rpc.annotation.Codecs;

public interface Client {

	/**
	 * invoke sync via rpc
	 * 
	 * @param targetInstanceName
	 *            server instance name
	 * @param methodName
	 *            server method name
	 * @param argTypes
	 *            server method arg types
	 * @param args
	 *            send to server request args
	 * @param timeout
	 *            rcp timeout
	 * @param codecType
	 *            serialize/deserialize type
	 * @return Object return response
	 * @throws Exception
	 *             if some exception,then will be throwed
	 */
	public Object invokeSync(String targetInstanceName, String methodName,
							 Class<?>[] argTypes, Object[] args, int timeout, Codecs codecType)
			throws Exception;

	/**
	 * receive response from server
	 */
	public void putResponse(ResponseWrapper response) throws Exception;
	
	/**
	 * server address
	 * 
	 * @return String
	 */
	public String getServerIP();

	/**
	 * server port
	 * 
	 * @return int
	 */
	public int getServerPort();

	/**
	 * connect timeout
	 * 
	 * @return
	 */
	public int getConnectTimeout();
	
	/**
	 * Get factory
	 */
	public ClientFactory getClientFactory();

}
