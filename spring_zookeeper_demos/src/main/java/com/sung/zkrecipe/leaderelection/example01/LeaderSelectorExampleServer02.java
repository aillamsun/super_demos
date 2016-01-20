package com.sung.zkrecipe.leaderelection.example01;

import com.google.common.collect.Lists;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by sungang on 2016/1/8.17:07
 */
public class LeaderSelectorExampleServer02 extends BaseExample {

    public static void main(String[] args) throws Exception {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<ExampleClient> examples = Lists.newArrayList();
        TestingServer server = new TestingServer();
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
            CuratorFramework client = CuratorFrameworkFactory.builder()
                    .connectString(server.getConnectString())
                    .authorization(SCHEMEN, AUTH.getBytes())
                    .retryPolicy(retryPolicy).namespace(NAME_SPACE).build();
            clients.add(client);
            ExampleClient example = new ExampleClient(client, PATH, "Client #" + "02");
            examples.add(example);
            client.start();
            example.start();
            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
            System.out.println("Shutting down...");
            for (ExampleClient exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
            CloseableUtils.closeQuietly(server);
        }
    }
}
