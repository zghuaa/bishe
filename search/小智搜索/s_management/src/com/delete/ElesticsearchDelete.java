package com.delete;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElesticsearchDelete {
    private static String ServerIP = "127.0.0.1";// ElasticSearch server ip
    private static int ServerPort = 9300;// port
//    public static void main(String[] args) {
//        try {
//            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "els").build();
//            TransportClient client = new TransportClient(settings);
//            client.addTransportAddress(new InetSocketTransportAddress(ServerIP, ServerPort));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        deleteIndex("information");//删除名为test的索引库
//    }

    public static void deleteIndex(String indexName) {
        try {
            if (!isIndexExists(indexName)) {
                System.out.println(indexName + " not exists");
            } else {
                Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "els").build();
                TransportClient client = new TransportClient(settings);
                client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
                DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName).execute().actionGet();
                if (dResponse.isAcknowledged()) {
                    System.out.println("delete index " + indexName + "  successfully!");
                } else {
                    System.out.println("Fail to delete index " + indexName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isIndexExists(String indexName) {
        boolean flag = false;
        try {
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", "els").build();
            TransportClient client = new TransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));

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
}
