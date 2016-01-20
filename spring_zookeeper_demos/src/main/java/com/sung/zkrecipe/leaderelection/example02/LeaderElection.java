package com.sung.zkrecipe.leaderelection.example02;

import com.sung.zkrecipe.TestMainClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by sungang on 2016/1/18.11:51
 */
public class LeaderElection extends TestMainClient {

    public static final Logger logger = Logger.getLogger(LeaderElection.class);

    public LeaderElection(String connectString, String rootPath){
        super(connectString);
        super.rootPath = rootPath;
        if (zk != null){
            try {
                Stat s = zk.exists(rootPath, false);
                if (s == null) {
                    zk.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            }catch (KeeperException e){
                logger.error(e);
            }catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }


    void findLeader() throws InterruptedException, UnknownHostException, KeeperException {
        byte[] leader = null;
        try {
            leader = zk.getData(rootPath + "/leader", true, null);
        } catch (KeeperException e) {
            if (e instanceof KeeperException.NoNodeException) {
                logger.error(e);
            } else {
                throw e;
            }
        }
        if (leader != null) {
            following();
        } else {
            String newLeader = null;
            byte[] localhost = InetAddress.getLocalHost().getAddress();
            try {
                //创建一个临时的节点
                newLeader = zk.create(rootPath + "/leader", localhost, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                if (e instanceof KeeperException.NodeExistsException) {
                    logger.error(e);
                } else {
                    throw e;
                }
            }
            if (newLeader != null) {
                leading();
            } else {
                mutex.wait();
            }
        }
    }

    void leading() {
        System.out.println("成为领导者");
    }

    void following() {
        System.out.println("成为组成员");
    }



    @Override
    public void process(WatchedEvent event) {
        final Event.EventType eventType = event.getType();
//        if (event.getPath().equals(rootPath + "/leader") && eventType == Event.EventType.NodeCreated) {
//            System.out.println("得到通知");
//            super.process(event);
//            following();
//        }
        if (Event.EventType.NodeDeleted.equals(eventType)) {
            if (event.getPath().equalsIgnoreCase(rootPath + "/leader")) {
                following();
            }
        }

    }


    public static void main(String[] args) {
        String connectString = "10.143.132.232:2181";// + TestMainServer.CLIENT_PORT;
        LeaderElection le = new LeaderElection(connectString, "/easy_view_conf");
        try {
            le.findLeader();
        } catch (Exception e) {
            logger.error(e);
        }

        while (true){
        }
    }
}
