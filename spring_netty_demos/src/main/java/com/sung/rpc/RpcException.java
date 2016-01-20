/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */
package com.sung.rpc;

/**
 * Create on @2013-8-20 @下午2:41:29
 * 
 * @author bsli@ustcinfo.com
 */
@SuppressWarnings("serial")
public class RpcException extends RuntimeException {
	public RpcException() {
		super();
	}

	public RpcException(String message) {
		super(message);
	}

	public RpcException(String message, Throwable cause) {
		super(message, cause);
	}
}
