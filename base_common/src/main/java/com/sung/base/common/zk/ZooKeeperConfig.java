package com.sung.base.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class ZooKeeperConfig implements Config {
	//根据路径，返回节点的值，并以;分开
	public String getConfig(String path) throws Exception {
		CuratorFramework client = ZooKeeperFactory.get();
		if (!exists(client, path)) {
			throw new RuntimeException("Path " + path + " does not exists.");
		}
		StringBuffer sb = new StringBuffer();
		List<String> lists = client.getChildren().forPath(path);
		for(String str:lists){
			byte[] data = client.getData().forPath(path+"/"+str);
			if(0 == data.length){
				sb.append(getConfig(path+"/"+str)+";");
			}else{
				sb.append(new String(data,"UTF-8")+";");
			}
		}
		return sb.toString();
	}

	public String getNode(String path) throws Exception {
		CuratorFramework client = ZooKeeperFactory.get();
		if (!exists(client, path)) {
			throw new RuntimeException("Path " + path + " does not exists.");
		}
		StringBuffer sb = new StringBuffer();
		byte[] data= client.getData().forPath(path);

				sb.append(new String(data,"UTF-8"));

		return sb.toString();
	}

	public void setConfigListener(final IZKListener listener) throws Exception {
		CuratorFramework client = ZooKeeperFactory.get();
		if (!exists(client, listener.getPath())) {
			System.out.println("Path " + listener.getPath() + " does not exists.");
			throw new RuntimeException("Path " + listener.getPath() + " does not exists.");
		} else {
			//执行侦听
			listener.executor();
		}
	}

	private boolean exists(CuratorFramework client, String path)
			throws Exception {
		try {
			Stat stat = client.checkExists().forPath(path);
			return !(stat == null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

}
