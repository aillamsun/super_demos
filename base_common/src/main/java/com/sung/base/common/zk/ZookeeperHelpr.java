package com.sung.base.common.zk;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class ZookeeperHelpr {

	private static CuratorFramework client = null;
	static {
		client = ZooKeeperFactoryBean.CLIENT;
	}

	// 根据路径，返回节点的值，并以;分开
	public static String getConfig(String path) throws Exception {
		if (!checkExist(path)) {
			throw new RuntimeException("Path " + path + " does not exists.");
		}
		StringBuffer sb = new StringBuffer();
		List<String> lists = client.getChildren().forPath(path);
		for (String str : lists) {
			byte[] data = client.getData().forPath(path + "/" + str);
			if (0 == data.length) {
				sb.append(getConfig(path + "/" + str) + ";");
			} else {
				sb.append(new String(data, "UTF-8") + ";");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取节点数据
	 * 
	 * @param path
	 * @return
	 */
	public static String getData(String path) {
		String data = null;
		try {
			if (StringUtils.isNotBlank(path) && checkExist(path)) {
				data = new String(client.getData().forPath(path), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 获取某个节点下的所有子文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getListChild(String path) {
		List<String> paths = Lists.newArrayList();
		try {
			if (StringUtils.isNotBlank(path) && checkExist(path)) {
				paths = client.getChildren().forPath(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paths;
	}

	/**
	 * 判断路径是否存在
	 * 
	 * @param path
	 * **/
	private static boolean checkExist(String path) throws Exception {
		if (client.checkExists().forPath(path) == null) {
			return false;
		}
		return true;
	}
}
