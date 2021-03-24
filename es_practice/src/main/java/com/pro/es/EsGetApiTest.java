package com.pro.es;

import org.elasticsearch.SecureSM;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

public class EsGetApiTest {

    public static void main(String[] args) {
        EsGetApiTest getApi = new EsGetApiTest();
        GetResponse resp = getApi.GetResponseSync("posts", "doc", "1");
        System.out.println(resp.getIndex());
        System.out.println(resp.getType());
        System.out.println(resp.getId());
        if (resp.isExists()) {
            System.out.println(resp.getVersion());
            System.out.println(resp.getSourceAsString());
            Map<String, Object> sourceAsString = resp.getSourceAsMap();
            for (Map.Entry<String, Object> entry : sourceAsString.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            byte[] sourceAsBytes = resp.getSourceAsBytes();
            System.out.println(sourceAsBytes);
        }

        getApi.GetResponseAsync("posts", "doc", "1");
    }

    public GetResponse GetResponseSync(String index, String type, String id) {
        GetRequest getRequest = new GetRequest(index, type, id);
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);
        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getResponse;
    }

    public void GetResponseAsync(String index, String type, String id) {
        GetRequest getRequest = new GetRequest(index, type, id);
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);
        client.getAsync(getRequest, new ActionListener<GetResponse>() {
            public void onResponse(GetResponse resp) {
                System.out.println(resp.getIndex());
                System.out.println(resp.getType());
                System.out.println(resp.getId());
                if (resp.isExists()) {
                    System.out.println(resp.getVersion());
                    System.out.println(resp.getSourceAsString());
                    Map<String, Object> sourceAsString = resp.getSourceAsMap();
                    for (Map.Entry<String, Object> entry : sourceAsString.entrySet()) {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    }
                    byte[] sourceAsBytes = resp.getSourceAsBytes();
                    System.out.println(sourceAsBytes);
                }
            }

            public void onFailure(Exception e) {
                System.out.println(e);
            }
        });

    }

}