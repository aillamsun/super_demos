package com.sung.es.java.example01;

import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * Created by sungang on 2016/1/21.11:38
 */
public class ElasticSearchHandler {

    private Client client;

    public ElasticSearchHandler(){
        this("192.168.3.141");
    }

    public ElasticSearchHandler(String ip){
        //集群连接超时设置
        /*
            Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout", "10s").build();
            client = new TransportClient(settings);
         */
        //client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ip, 9300));


//        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "localtestsearch").build();
//        TransportClient transportClient = new TransportClient(settings);
//        transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
//        return (Client) transportClient;

    }
}
