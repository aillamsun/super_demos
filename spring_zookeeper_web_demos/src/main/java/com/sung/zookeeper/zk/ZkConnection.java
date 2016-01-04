package com.sung.zookeeper.zk;

import com.sung.base.common.utils.LogUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZkConnection {

	private ZooKeeper _zk = null;

	private final Lock _zookeeperLock = new ReentrantLock();

	private final String _servers;
	private final int _sessionTimeOut;

	private static final Method method;

	private Watcher _watcher;

	static {
		Method[] methods = ZooKeeper.class.getDeclaredMethods();
		Method m = null;
		for (Method method : methods) {
			if (method.getName().equals("multi")) {
				m = method;
				break;
			}
		}
		method = m;
	}

	public ZkConnection(String zkServers, int sessionTimeOut) {
		_servers = zkServers;
		_sessionTimeOut = sessionTimeOut;
	}

	/**
	 * @Title: connect
	 * @author：孙刚
	 * @date：2014年9月2日 下午1:29:45
	 * @Description: 创建 ZK 服务器连接
	 * @param：@param watcher
	 * @return：void返回类型
	 * @throws
	 */
	public void connect(Watcher watcher) {
		this._watcher = watcher;
		if (null == this._watcher) {
			this._watcher = new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					LogUtils.logInfo("我是一个空闲的 Watcher ，不要监听任何事件的 Watcher ！");
				}
			};
		} else {
			// 加锁
			_zookeeperLock.lock();
			try {
				if (_zk != null) {
					throw new IllegalStateException(
							"zk client has already been started");
				}
				LogUtils.logInfo("创建一个 ZooKeeper instance connect to 【"
						+ _servers + "】.");
				try {
					_zk = new ZooKeeper(_servers, _sessionTimeOut, _watcher);
				} catch (IOException e) {
					LogUtils.logError("Unable Zookeeper Instance connect to 【 "
							+ _servers + " 】", e);
				}
			} finally {
				_zookeeperLock.unlock();
			}
		}
	}

	/**
	 * @Title: close
	 * @author：孙刚
	 * @date：2014年9月2日 下午1:32:37
	 * @Description:关闭 Zookeeper 连接
	 * @param：
	 * @return：void返回类型
	 * @throws
	 */
	public void close() {
		_zookeeperLock.lock();
		try {
			if (_zk != null) {
				LogUtils.logInfo("Closing ZooKeeper connected to 【 " + _servers
						+ "】");
				_zk.close();
				_zk = null;
			}
		} catch (InterruptedException e) {
			LogUtils.logError("Closing Zookeeper connected fail !", e);
		} finally {
			_zookeeperLock.unlock();
		}
	}

	// 创建节点
	public String create(String path, byte[] data, CreateMode createMode)
			throws KeeperException, InterruptedException {
		return _zk.create(path, data, Ids.CREATOR_ALL_ACL, createMode);
	}

	// 删除节点
	public void delete(String path) throws InterruptedException,
			KeeperException {
		_zk.delete(path, -1);
	}

	// 判断节点是否存在
	public boolean exists(String path, boolean watch) throws KeeperException,
			InterruptedException {
		return _zk.exists(path, watch) != null;
	}

	// 获取子节点
	public List<String> getChildren(final String path, final boolean watch)
			throws KeeperException, InterruptedException {
		return _zk.getChildren(path, watch);
	}

	// 读取节点数据
	public byte[] readData(String path, Stat stat, boolean watch)
			throws KeeperException, InterruptedException {
		return _zk.getData(path, watch, stat);
	}

	// 写数据
	public Stat writeData(String path, byte[] data, int version)
			throws KeeperException, InterruptedException {
		return _zk.setData(path, data, version);
	}

	public States getZookeeperState() {
		return _zk != null ? _zk.getState() : null;
	}

	// 获取某个节点的创建时间
	public long getCreateTime(String path) throws KeeperException,
			InterruptedException {
		Stat stat = _zk.exists(path, false);
		if (stat != null) {
			return stat.getCtime();
		}
		return -1;
	}

	public List<?> multi(Iterable<?> ops) {
		if (method == null) {
			throw new UnsupportedOperationException(
					"multi operation must use zookeeper 3.4+");
		}
		try {
			return (List<?>) method.invoke(_zk, ops);
		} catch (IllegalArgumentException e) {
			throw new UnsupportedOperationException(
					"ops must be 'org.apache.zookeeper.Op'");
		} catch (IllegalAccessException e) {
			throw new UnsupportedOperationException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public ZooKeeper getZooKeeper() {
		return _zk;
	}

	public String getServers() {
		return _servers;
	}
}
