package com.sung.rpc.protocol;

import com.sung.rpc.server.RPCServerHandler;
import com.sung.rpc.server.ServerHandler;

public class ProtocolUtils {

	private static final RPCProtocol protocol = new RPCProtocol();
	
	private static final ServerHandler serverHandler = new RPCServerHandler();
	
	public static ByteBufferWrapper encode(Object message,ByteBufferWrapper bytebufferWrapper) throws Exception {
		return protocol.encode(message, bytebufferWrapper);
	}

	public static Object decode(ByteBufferWrapper wrapper, Object errorObject) throws Exception {
		return protocol.decode(wrapper, errorObject);
	}
	
	public static ServerHandler getServerHandler(){
		return serverHandler;
	}

}
