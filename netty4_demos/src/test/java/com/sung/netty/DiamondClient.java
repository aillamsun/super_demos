/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.sung.netty;


import com.sung.netty.client.ClientChannelInitializer;
import com.sung.netty.client.Netty4Client;

/**
 * Create on @2013-8-24 @上午10:07:10 
 * @author bsli@ustcinfo.com
 */
public class DiamondClient {
	public static void main(String[] args) throws Exception {
		Netty4Client client = new Netty4Client("localhost", 8080, new ClientChannelInitializer());
		
		while(client.isConnected()) {
			System.out.println(client.receiveMessage());
		}
		
		System.out.println("=============");
		
		client.close();
	}
}
