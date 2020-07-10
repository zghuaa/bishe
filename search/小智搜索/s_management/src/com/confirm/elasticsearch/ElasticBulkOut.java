package com.confirm.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

/*
留言批量导出到txt中
 */
public class ElasticBulkOut {
    public static void outElasticSearch(){

        try {
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", "els").build();
            TransportClient client = new TransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1",
                    9300));

//            QueryBuilder qb = QueryBuilders.matchAllQuery();
            SearchResponse response = client.prepareSearch("information")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .execute().actionGet();
            SearchHits resultHits = response.getHits();

            File article = new File("/home/gly/liuyan.txt");
            FileWriter fw = new FileWriter(article);
            BufferedWriter bfw = new BufferedWriter(fw);

            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");

            } else {
                for (int i = 0; i < resultHits.getHits().length; i++) {
                    String jsonStr = resultHits.getHits()[i]
                            .getSourceAsString();
                    System.out.println(jsonStr);
                    bfw.write(jsonStr);
                    bfw.write("\n");
                }
            }
            bfw.close();
            fw.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        outElasticSearch();
    }
}
