/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.sung.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create on @2013-8-21 @上午10:37:53 
 * @author bsli@ustcinfo.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcService {

	/**
	 * 客户端 invoke method 超时时间，默认值 1000ms
	 */
	public int timeout() default 1000;
	
	/**
	 * 客户端请求和服务器端响应编码器
	 */
	public Codecs encoder();
}
