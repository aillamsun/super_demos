package com.sung.zkrecipe.leaderelection.example01;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by sungang on 2016/1/8.17:06
 */
public class LeaderSelectorExampleServer01 extends BaseExample {

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        ExampleClient example = null;
        CuratorFramework client = null;
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
            client = CuratorFrameworkFactory.builder().connectString(server.getConnectString())
                    .authorization(SCHEMEN, AUTH.getBytes()).retryPolicy(retryPolicy).namespace(NAME_SPACE).build();
            //example = new ExampleClient(client, PATH, "Client #" + "01");
            client.start();
            //example.start();
            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

            Watcher watcher = new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("path-->:" + event.getPath());
                }
            };
            client.checkExists().usingWatcher(watcher).forPath(PATH);
        } finally {
            System.out.println("Shutting down...");
            //CloseableUtils.closeQuietly(example);
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(server);
        }
    }

}
