package com.ServletOpt;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class InsertOpt {
    HttpServletRequest request;
    HttpServletResponse response;
    public InsertOpt(){super();}
    public InsertOpt(HttpServletRequest request, HttpServletResponse response){
        this.request=request;
        this.response=response;
    }

    public void updateQuestion() throws IOException {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "els").build();
        TransportClient client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1",
                9300));
        String json=request.getReader().readLine();    //接收前端返回来的json字符串
        System.out.println(json);
        JSONObject jsonObject=JSON.parseObject(json);
        String id=jsonObject.getString("id");    //问题id
        String tijaoren=jsonObject.getString("tijiaoren");    //提交人
        String answer=jsonObject.getString("answer");    //答案
        System.out.println(id+"++++"+tijaoren+"============"+answer+"************");

        //生成提交时间
        Date d=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyy-MM--dd HH:mm:ss");
        String tjsj=format.format(d);     //提交时间

        //往question库里更新数据
        try {
            UpdateRequest uRequest = new UpdateRequest();
            uRequest.index("question");
            uRequest.type("fankui");
            uRequest.id(id);
            uRequest.doc(XContentFactory.jsonBuilder().startObject().field("answer",answer).field("tijaoren",tijaoren).field("tijiaoshijian",tjsj).endObject());
            try {
                UpdateResponse indexResponse=client.update(uRequest).get();
                System.out.println(indexResponse.getGetResult());
                client.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
