package com.sung.zookeeper.zk;

import com.sung.zookeeper.config.ServerConfig;
import org.apache.zookeeper.ZooKeeper;

public class ZkHelper {

	private static ZkClient zkClient;

	private static ZooKeeper zooKeeper;

	public ZkHelper() {
		zkClient = new ZkClient(ServerConfig.zk_host + ":"
				+ ServerConfig.zk_port, 30000, 30000);
		zooKeeper = zkClient.getZooKeeper();
		// 认证
		zooKeeper.addAuthInfo(ServerConfig.zk_auth_type,
				ServerConfig.zk_auth_passwd.getBytes());
	}

	public static ZkClient getZkClient() {
		return zkClient;
	}

	public static ZooKeeper getZooKeeper() {
		return zooKeeper;
	}
}
