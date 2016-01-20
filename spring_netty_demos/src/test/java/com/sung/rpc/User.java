/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.sung.rpc;

import java.io.Serializable;

/**
 * Create on @2013-8-21 @上午11:36:55 
 * @author bsli@ustcinfo.com
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	private String username;
	
	public User(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
