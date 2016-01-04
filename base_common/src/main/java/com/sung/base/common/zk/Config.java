package com.sung.base.common.zk;

public interface Config {
	
	public String getConfig(String path) throws Exception;
	public String getNode(String path) throws Exception;
	public void setConfigListener(IZKListener listeners) throws Exception;
}
