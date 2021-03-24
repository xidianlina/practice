package com.pro.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EsBase {
    public static RestHighLevelClient GetEsClient(String host, int port) {
        RestClient lowLevelRestClient = RestClient.builder(new HttpHost(host, port, "http")).build();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);
        return client;
    }
}
