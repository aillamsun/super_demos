package com.sung.rpc;

import com.sung.rpc.RpcClientFactory;

/**
 * Created by sungang on 2016/1/20.17:06
 */
public class ClientTest {

    public static void main(String[] args) {
        HelloWorld helloWorld = RpcClientFactory.refer(HelloWorld.class, "localhost", 8080);
        Result result = helloWorld.queryUser(new User("sungang"));
        System.out.println(result.getMessage());

    }
}
