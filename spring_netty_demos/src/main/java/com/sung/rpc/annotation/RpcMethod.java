/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.sung.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create on @2013-8-21 @下午1:46:11 
 * @author bsli@ustcinfo.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RpcMethod {
	/**
	 * 客户端 invoke method 超时时间，默认值 1000ms
	 */
	public int timeout() default 1000;
}
