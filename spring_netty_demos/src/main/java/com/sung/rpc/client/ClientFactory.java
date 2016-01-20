package com.sung.rpc.client;

public interface ClientFactory {
	
	/**
	 * get client,default targetIP:targetPort --> one connection
	 * u can give custom the key by give customKey
	 */
	public Client get(final String targetIP, final int targetPort,
					  final int connectTimeout) throws Exception;

	/**
	 * get client,create clientNums connections to targetIP:targetPort(or your custom key)
	 */
	public Client get(final String targetIP, final int targetPort,
					  final int connectTimeout, final int clientNums)
			throws Exception;

	/**
	 * remove some error client
	 */
	public void removeClient(String key);

}