package com.sung.rpc.server;

public interface Server {

	/**
	 * start server at listenPort,requests will be handled in businessThreadPool
	 */
	public void start(int listenPort) throws Exception;
	
	/**
	 * register business handler
	 */
	public void registerProcessor(String serviceName, Object serviceInstance);
	
	/**
	 * stop server
	 */
	public void stop() throws Exception;
	
}
