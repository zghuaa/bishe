package com.confirm.elasticsearch;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class Delete {

    public static void main(String[] args) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "els").build();
        TransportClient client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1",
                9300));
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete("information")
                .execute().actionGet();
    }
}
