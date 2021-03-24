package com.pro.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class EsSearchApi {
    public static void main(String[] args) {
        EsSearchApi searchApi = new EsSearchApi();
        searchApi.SearchResult();
    }

    public void SearchResult() {
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);

        SearchRequest req = new SearchRequest("posts");
        req.types("doc");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        req.source(builder);
        SearchResponse resp = null;
        try {
            resp = client.search(req);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchHits hits = resp.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String id = hit.getId();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String title = (String) sourceAsMap.get("title");
            System.out.println(id + ":" + title);
        }
    }
}
