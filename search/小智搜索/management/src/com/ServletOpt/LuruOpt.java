package com.ServletOpt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LuruOpt {
    HttpServletRequest request;
    HttpServletResponse response;
    public LuruOpt(){super();}
    public LuruOpt(HttpServletRequest request, HttpServletResponse response){
        this.request=request;
        this.response=response;
    }
    public void LuruMethod(String department,String title,UUID uuid,String tijiaoren,String system){
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "els").build();
        TransportClient client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1",
                9300));

        //生成提交时间
        Date d=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyy-MM--dd HH:mm:ss");
        String tjsj=format.format(d);     //提交时间

        String json="{\"mainType\":\""+department+"\",\"title\":\""+title+"\",\"content\":\"/web/resources/html/"+uuid+".html\",\"score\":\"0\",\"tijaoren\":\""+tijiaoren+"\",\"subType\":\""+system+"\",\"tijiaoshijian\":\""+tjsj+"\"}";
        IndexResponse indexResponse = client.prepareIndex("question", "ziliao").setSource(json).execute().actionGet();

    }
}
