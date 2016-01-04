package com.sung.base.common.zk;

import com.sung.base.common.utils.LogUtils;
import com.sung.base.common.utils.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class ZooKeeperFactoryBean {
    /**
     * 连接字符串信息 IP:PORT
     */
    public static String connectString;

    public static int sessionTimeoutMs;

    public static int connectionTimeoutMs;

    public static int maxRetries;

    public static int baseSleepTimeMs;

    public static String authType;

    public static String authPassword;

    public static String rootNodeName;

    public static CuratorFramework CLIENT = null;

    /**
     * 连接zk server
     */
    public static CuratorFramework initCuratorFramework() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        try {
            if (ObjectUtil.isBlank(CLIENT)) {
                CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
                builder.connectString(connectString)
                        .retryPolicy(retryPolicy)
                        .connectionTimeoutMs(connectionTimeoutMs)
                        .sessionTimeoutMs(sessionTimeoutMs)
                        .namespace(rootNodeName);
                if (StringUtils.isNotBlank(authPassword)) {
                    builder.authorization(authType, authPassword.getBytes());
                }
                LogUtils.logInfo("---connection success：[" + connectString + "]---");
                /**
                 * 添加zk 连接状态监听
                 */
                CLIENT = builder.build();
                //注册监听事件
                //registerListeners(CLIENT);
                CLIENT.start();
            }
        } catch (Exception e) {
            LogUtils.logError("connect zookeeper fail......", e);
        }
        return CLIENT;
    }


    public static void colse() throws Exception {
        if (CLIENT != null) {
            // Note we cannot use zkClient.close()
            // since you cannot currently close a client which is not connected
            CLIENT.close();
            CLIENT = null;
        }
    }


    /**
     * 注册需要监听的事件
     */
    public static void registerListeners(final List<IZookeeperListener> listeners) {
        try {
            //连接状态监听
            CLIENT.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                    LogUtils.logInfo("CuratorFramework state changed: ", newState);
                    if (newState == ConnectionState.CONNECTED || newState == ConnectionState.RECONNECTED) {
                        if (ObjectUtil.isNotBlank(listeners)) {
                            for (IZookeeperListener listener : listeners) {
                                listener.executor(client);
                                LogUtils.logInfo("Zookeeper Listener executed!", listener.getClass().getName());
                            }
                        }
                    } else {
                        LogUtils.logInfo("CuratorFramework state not connect...");
                    }
                }
            });
            CLIENT.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
                @Override
                public void unhandledError(String message, Throwable e) {
                    LogUtils.logInfo("CuratorFramework unhandledError:", message);
                }
            });
        } catch (Exception e) {

        }
    }
}
