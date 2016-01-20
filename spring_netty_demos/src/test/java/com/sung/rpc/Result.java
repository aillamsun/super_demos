/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.sung.rpc;

import java.io.Serializable;

/**
 * Create on @2013-8-21 @上午11:39:16 
 * @author bsli@ustcinfo.com
 */
@SuppressWarnings("serial")
public class Result implements Serializable {
	private String message;
	
	public Result(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
