package com.sung.zkrecipe.leaderelection.example03;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by sungang on 2016/1/18.14:50
 * This class is responsible for interacting with ZooKeeper cluster by connecting to ZooKeeper service,
 * creating, deleting and getting znodes and setting the watch on znodes.
 */
public class ZooKeeperService {
    private ZooKeeper zooKeeper;

    public ZooKeeperService(final String url, final ProcessNode.ProcessNodeWatcher processNodeWatcher) throws IOException {
        zooKeeper = new ZooKeeper(url, 3000, processNodeWatcher);
        zooKeeper.addAuthInfo("digest","password".getBytes());
    }

    public String createNode(final String node, final boolean watch, final boolean ephimeral) {
        String createdNodePath = null;
        try {
            final Stat nodeStat = zooKeeper.exists(node, watch);
            if (nodeStat == null) {
                createdNodePath = zooKeeper.create(node, new byte[0], Ids.OPEN_ACL_UNSAFE, (ephimeral ? CreateMode.EPHEMERAL_SEQUENTIAL : CreateMode.PERSISTENT));
            } else {
                createdNodePath = node;
            }
        } catch (KeeperException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return createdNodePath;
    }

    public boolean watchNode(final String node, final boolean watch) {
        boolean watched = false;
        try {
            final Stat nodeStat = zooKeeper.exists(node, watch);
            if (nodeStat != null) {
                watched = true;
            }
        } catch (KeeperException | InterruptedException e) {
            throw new IllegalStateException(e);
        }

        return watched;
    }

    public List<String> getChildren(final String node, final boolean watch) {
        List<String> childNodes = null;
        try {
            childNodes = zooKeeper.getChildren(node, watch);
        } catch (KeeperException | InterruptedException e) {
            throw new IllegalStateException(e);
        }

        return childNodes;
    }

}
