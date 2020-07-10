package com.delete;


import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticSearchCreate {

    private static Settings settings = null;
    private static TransportClient client = null;

    public ElasticSearchCreate(){
        settings = ImmutableSettings.settingsBuilder().put("cluster.name", "els").build();
        client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
    }

    public void createIndex(String indexName) {
        try {
            // 创建索引库
            if (isIndexExists(indexName)) {
                System.out.println("Index  " + indexName + " already exits!");
            } else {
                CreateIndexRequest cIndexRequest = new CreateIndexRequest(indexName);
                CreateIndexResponse cIndexResponse = client.admin().indices().create(cIndexRequest).actionGet();
                if (cIndexResponse.isAcknowledged()) {
                    System.out.println("create index successfully！");
                } else {
                    System.out.println("Fail to create index!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isIndexExists(String indexName) {
        boolean flag = false;
        try {
            IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);
            IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
            if (inExistsResponse.isExists()) {
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public static void main(String[] args) {
        ElasticSearchCreate elasticSearchCreate = new ElasticSearchCreate();
        elasticSearchCreate.createIndex("question");
//        createIndex("no");
    }
}


