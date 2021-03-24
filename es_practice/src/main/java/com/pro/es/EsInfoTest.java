package com.pro.es;

import org.apache.lucene.analysis.el.GreekStemFilter;
import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;

import java.io.IOException;

public class EsInfoTest {
    public static void main(String[] args) {
        EsInfoTest info = new EsInfoTest();
        info.GetEsInfo();
    }

    public void GetEsInfo() {
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);

        try {
            MainResponse response = client.info();
            ClusterName clusterName = response.getClusterName();
            String clusterUuid = response.getClusterUuid();
            String nodeName = response.getNodeName();
            Version version = response.getVersion();
            Build build = response.getBuild();
            System.out.println(clusterName);
            System.out.println(clusterUuid);
            System.out.println(nodeName);
            System.out.println(version);
            System.out.println(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
