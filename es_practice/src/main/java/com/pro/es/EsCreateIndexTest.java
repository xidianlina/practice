package com.pro.es;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EsCreateIndexTest {
    public static void main(String[] args) {
        EsCreateIndexTest esTest = new EsCreateIndexTest();
        esTest.CreateIndexByString();
        esTest.CreateIndexByMap();
        esTest.CreateIndexByXContentBuilder();
        esTest.CreateIndexBySource();
        esTest.CreateIndexAsync();
    }

    public void CreateIndexByString() {
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);

        IndexRequest request = new IndexRequest("posts", "doc", "1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(indexResponse);
    }

    public void CreateIndexByMap() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("user", "test");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts2", "doc", "1").source(jsonMap);

        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(indexResponse);
    }

    public void CreateIndexByXContentBuilder() {
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("user", "user2");
                builder.field("postData", new Date());
                builder.field("message", "trying out es");
            }

            builder.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);
        IndexRequest indexRequest = new IndexRequest("post3", "doc", "1").source(builder);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(indexResponse);
    }

    public void CreateIndexBySource() {
        IndexRequest indexRequest = new IndexRequest("post5", "doc", "1")
                .source("user", "user7", "postDate", new Date(), "message", "dfajkdl");
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(indexResponse);
    }

    public void CreateIndexAsync(){
        IndexRequest indexRequest = new IndexRequest("post000", "doc", "1")
                .source("user", "user00", "postDate", new Date(), "message", "kibana");
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);
        client.indexAsync(indexRequest, new ActionListener<IndexResponse>() {
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse);
            }

            public void onFailure(Exception e) {
                System.out.println(e);
            }
        });
    }
}
