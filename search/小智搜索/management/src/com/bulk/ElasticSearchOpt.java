package com.bulk;

/**
 * Created by WQ on 16-8-11.
 */

import com.alibaba.fastjson.JSON;
import com.bulk.IElasticSearchOpt.IElasticSearchOpt;
import com.dao.Information;
import com.dao.MyRequest;
import com.dao.Question;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


@SuppressWarnings(value="unchecked")
public class ElasticSearchOpt implements IElasticSearchOpt {
    private static Logger logger = Logger.getLogger(ElasticSearchOpt.class);
    private static Settings settings = null;
    private static TransportClient client = null;
    private static String IP = "127.0.0.1";
    private static int PORT = 9300;
    private static int SIZE = 5;
    private static String OK = "{\"status\":\"0\"}";

    public ElasticSearchOpt() {
        if (settings == null) {
            settings = ImmutableSettings.settingsBuilder().put("cluster.name", "els").build();
        }
        if (client == null) {
            client = new TransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress(IP, PORT));
        }
    }

    @Override
    public String addJson(Information information) {
        try {
            if (information.getDate() == null) {
                information.setDate(getDate());
            }
            String jsonStr = JSON.toJSONString(information);
            System.out.println(jsonStr);
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            bulkRequest.add(client.prepareIndex(information.getIndexName(), information.getType()).setSource(jsonStr));
            bulkRequest.execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
        return "0";
    }

    @Override
    public String addQuestion(Question question) {
        try {
            String jsonStr = JSON.toJSONString(question);
            System.out.println(jsonStr);
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            bulkRequest.add(client.prepareIndex(question.getIndexName(), question.getType()).setSource(jsonStr));
            bulkRequest.execute().actionGet();
        } catch (Exception e) {
            logger.error("fun:addQuestion", e);
            return "1";
//            return "{\"status\":\"fail\"}";
        }
        return "0";
//        return "{\"status\":\"ok\"}";
    }

    /**
     * 删除
     */
    @Override
    public String delDocument(Information information) {
        String status = "0";
        try {
            DeleteByQueryResponse dResponse = client
                    .prepareDeleteByQuery(information.getIndexName())
                    .setTypes(information.getType())
                    .setQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("title", information.getTitle())))
                    .execute().actionGet();
            status = "1";
        } catch (Exception e) {
            logger.error("fun:  delDocument", e);
        }
        return status;
    }

    /**
     * 删除
     */
    @Override
    public String delQuestion(Question question) {
        String status = "0";
        try {
            DeleteByQueryResponse dResponse = client
                    .prepareDeleteByQuery("question")
                    .setTypes("fankui")
                    .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("email", "15810227076@163.com")))
                    .execute().actionGet();
            status = "1";
        } catch (Exception e) {
            logger.error("fun:  delQuestion", e);
        }
        return status;
    }

    public String searchAlways(Information information) {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            SearchResponse response = client.prepareSearch(information.getIndexName())
                    .setTypes("always")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .execute().
                            actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
            } else {
                for (int i = 0; i < resultHits.getHits().length; i++) {
                    String jsonStr = resultHits.getHits()[i].getSourceAsString();
                    System.out.println(resultHits.getHits()[i].getId() + "\t" + jsonStr);
                    arrayList.add(jsonStr);
                }
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }

    @Override
    public String deleteById(String id) {
        String status = "No";
        try {
            logger.info("Method: deleteById");
            DeleteResponse dResponse = client.prepareDelete("question", "fankui", id).execute().actionGet();
            if (dResponse.isFound()) {
                System.out.println("删除成功");
                status = "Ok";
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            logger.error("Method:  deleteById", e);
        }
        return status;
    }

    @Override
    public SearchHits searchByDate(Information information) {
        SearchResponse response = null;
        try {
            MatchQueryBuilder mpq1 = QueryBuilders.matchPhraseQuery("date", information.getDate());
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(mpq1);
            response = client.prepareSearch("information", "question")
                    .setTypes("visit", "fankui", "userVisit")
                    .setQuery(builder)
                    .setSize(Integer.MAX_VALUE)
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            logger.error("Method: searchByDate\t\tpara: " + information.toString() + "\t\t", e);
            return null;
        }
        return response.getHits();
    }

    private static String getDate() {
        Date date = new Date();
        SimpleDateFormat smpt = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        return smpt.format(date);
    }

    @Override
    public String searchQuestion() {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            SearchResponse response = client.prepareSearch("question")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setSize(Integer.MAX_VALUE)
                    .execute()
                    .actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
                return null;
            }
            for (SearchHit hit : resultHits) {
                hit.getSource().put("id", hit.getId());
                arrayList.add(JSON.toJSONString(hit.getSource()));
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }

    public SearchResponse searchSP() {
        SearchResponse response = null;
        try {
            response = client.prepareSearch("question")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setSize(Integer.MAX_VALUE)
                    .execute()
                    .actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
                return null;
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        return response;
    }


    public SearchResponse searchFKui(MyRequest myRequest) {
        SearchResponse response = null;
        try {
            response = client.prepareSearch("question")
                    .setTypes(myRequest.getType())
                    .setQuery(QueryBuilders.matchQuery("mainType",myRequest.getMainType()))
                    .setSize(Integer.MAX_VALUE)
                    .execute()
                    .actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
                return null;
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public String searchById(MyRequest myRequest) {
        GetResponse getResponse = client.prepareGet("question", myRequest.getType(), myRequest.getId()).execute().actionGet();
        getResponse.getSource().put("id", getResponse.getId());
        System.out.println(getResponse.getSource());
        return JSON.toJSONString(getResponse.getSource());
    }

    @Override
    public String searchQByType(MyRequest myRequest) {
        SearchResponse response = null;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            MatchQueryBuilder mpq1 = QueryBuilders.matchPhraseQuery("mainType", myRequest.getMainType());
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(mpq1);
            response = client.prepareSearch("question")
                    .setTypes(myRequest.getType())
                    .setQuery(builder)
                    .setSize(Integer.MAX_VALUE)
                    .execute()
                    .actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
                return null;
            }
            for (SearchHit hit : resultHits) {
                hit.getSource().put("id", hit.getId());
                arrayList.add(JSON.toJSONString(hit.getSource()));
            }
        } catch (Exception e) {
            return null;
        }
        String json = JSON.toJSONString(arrayList);
        return json;
    }

    @Override
    public String updateStatusById(MyRequest myRequest) {
        GetResponse myresponse = client.prepareGet(myRequest.getIndexName(), myRequest.getType(), myRequest.getId()).get();
        System.out.println(myresponse.getSourceAsString());
        myresponse.getSource().put("status", myRequest.getStatus());
        System.out.println(JSON.toJSONString(myresponse.getSource()));
        String updateJson = JSON.toJSONString(myresponse.getSource());
        UpdateResponse response = client.prepareUpdate(myRequest.getIndexName(), myRequest.getType(), myRequest.getId())
                .setDoc(updateJson)
                .execute()
                .actionGet();
        return OK;
    }

    public static void main(String[] args) {
        ElasticSearchOpt elasticSearchOpt = new ElasticSearchOpt();
        HashMap<String, String> hashMap = new HashMap();
//        hashMap.put("date",getDate());
//        hashMap.put("email","158102270762163.com");
//        hashMap.put("mainType","跨境电商");
//        hashMap.put("subType","申报");
//        hashMap.put("title","跨境电商");
//        hashMap.put("question","申报");
//        hashMap.put("status","0");
//        hashMap.put("tijaoren","王骞");
//        hashMap.put("tijiaoshijian",getDate());
//        hashMap.put("shenpishijian",getDate());
//        hashMap.put("answer","");
//        Question question = new Question(hashMap);
        for (int i = 0; i < 20; i++) {
//            System.out.println(elasticSearchOpt.addQuestion(question));
        }
    }
}