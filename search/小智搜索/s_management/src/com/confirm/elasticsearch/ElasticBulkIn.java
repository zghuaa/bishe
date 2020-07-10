package com.confirm.elasticsearch;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.*;
import java.net.UnknownHostException;

/*
txt问答文档信息批量导入存elasticsearch
 */
public class ElasticBulkIn {
    public  void inElasticSearch(){
        try {
            String route="C://project/information.txt";
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", "els").build();
            TransportClient client = new TransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1",
                    9300));

            File article = new File(route);
            FileReader fr=new FileReader(article);
            BufferedReader bfr=new BufferedReader(fr);
            String line=null;
            BulkRequestBuilder bulkRequest=client.prepareBulk();
            int count=0;
            while((line=bfr.readLine())!=null){
                bulkRequest.add(client.prepareIndex("information","税费支付").setSource(line));
                if (count%10==0) {
                    bulkRequest.execute().actionGet();
                }
                count++;
            }
            bulkRequest.execute().actionGet();

            bfr.close();
            fr.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        ElasticBulkIn el=new ElasticBulkIn();
        el.inElasticSearch();
    }

}
