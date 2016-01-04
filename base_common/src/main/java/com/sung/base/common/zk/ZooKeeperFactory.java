package com.sung.base.common.zk;

import com.sung.base.common.utils.LogUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZooKeeperFactory {
	public static String CONNECT_STRING;

	public static int MAX_RETRIES;

	public static int BASE_SLEEP_TIMEMS;

	public static String NAME_SPACE;

	public static String SCHEMEN;

	public static String AUTH;

	private static CuratorFramework client = null;

	public synchronized static CuratorFramework get() {
		if (null == client) {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIMEMS, MAX_RETRIES);
			client = CuratorFrameworkFactory.builder()
					.connectString(CONNECT_STRING)
					.authorization(SCHEMEN, AUTH.getBytes())
					.retryPolicy(retryPolicy).namespace(NAME_SPACE).build();
			LogUtils.logInfo("-----------------------打开ZK链接---------------------------");
			client.start();
		} else
			return client;
		return client;
	}
}
