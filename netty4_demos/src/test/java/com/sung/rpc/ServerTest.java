package com.sung.rpc;

import com.sung.rpc.netty4.Netty4Server;

/**
 * Created by sungang on 2016/1/20.16:54
 */
public class ServerTest {

    public static void main(String[] args) {

        Netty4Server server = new Netty4Server(10);
        server.registerProcessor(HelloWorld.class.getName(),new HelloWorldImpl());
        try {
            server.start(8080);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
