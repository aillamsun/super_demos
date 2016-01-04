package com.sung.base.common.zk;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author sungang
 */
public interface IZookeeperListener {
	
	void executor(CuratorFramework client);
	
}
