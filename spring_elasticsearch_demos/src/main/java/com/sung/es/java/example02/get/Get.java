package com.sung.es.java.example02.get;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sungang on 2016/3/2.16:00
 */
public class Get {




    public static void main(String[] args) {
        new Get().run();
    }


    public void run(){
        Map<String, String> m = new HashMap<>();
        // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，
        // 把集群中其它机器的ip地址加到客户端中，

        Client client = null;
        TransportClient transportClient = null;

        try {

            //transportClient = TransportClient.builder().addPlugin(ShieldPl)
        }finally {

        }
    }
}
