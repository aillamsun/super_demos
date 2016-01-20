package com.sung.zkrecipe;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/18.11:37
 */
public class TestMainClient implements Watcher{


    protected static ZooKeeper zk = null;
    protected static Integer mutex;
    int sessionTimeout = 10000;
    protected String rootPath;

    protected String zk_auth_type = "digest";
    private String zk_auth_passwd = "password";


    public TestMainClient(String connectString){
        if(zk == null){
            try {
                //String configFile = this.getClass().getResource("/").getPath()+"com/sung/zkrecipe/log4j.xml";
                //DOMConfigurator.configure(configFile);
                System.out.println("创建一个新的连接:");
                zk = new ZooKeeper(connectString, sessionTimeout, this);
                zk.addAuthInfo(zk_auth_type, zk_auth_passwd.getBytes());
                mutex = new Integer(-1);
            }catch (IOException e){
                zk = null;
                e.printStackTrace();
            }
        }
    }


    @Override
    public void process(WatchedEvent event) {
        synchronized (mutex){
            mutex.notify();
        }
    }
}
