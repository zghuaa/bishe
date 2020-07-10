package com.bulk;

/**
 * Created by WQ on 16-8-11.
 */

import com.Db.dao.Question;
import com.Db.dao.UserDao;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bulk.IElasticSearchOpt.IElasticSearchOpt;
import com.Db.dao.Information;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.*;


@SuppressWarnings(value="unchecked")
public class ElasticSearchOpt implements IElasticSearchOpt {
    private static Logger logger = Logger.getLogger(ElasticSearchOpt.class);
    private Settings settings = null;
    private TransportClient client = null;
    private static String IP = "127.0.0.1";
    private static int PORT = 9300;
    private static int SIZE = 5;

    public ElasticSearchOpt() {
        settings = ImmutableSettings.settingsBuilder().put("cluster.name", "els").build();
        client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(IP, PORT));
    }

    public String addQuestion(Question question) {
        try {
            String jsonStr = JSON.toJSONString(question);
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
     * 添加信息
     * */

    public String addJson(Information information) {
        try {
            String jsonStr = JSON.toJSONString(information);
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            bulkRequest.add(client.prepareIndex(information.getIndexName(), information.getType()).setSource(jsonStr));
            bulkRequest.execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
        return "0";
    }

    /**
     * 删除
     * */
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

    @Override
    public String searchType(Information information) {
        return null;
    }

    public String searchSubType(Information information) {
        ArrayList<Information> arrayList = new ArrayList();
        SearchResponse response = client.prepareSearch(information.getIndexName())
                .setTypes(information.getType())
                .setQuery(QueryBuilders.matchQuery("mainType", information.getMainType()))
                .execute()
                .actionGet();
        SearchHits resultHits = response.getHits();
        for (SearchHit hit : resultHits) {
            hit.getSource().put("id",hit.getId());
            System.out.println(hit.getSource());
            Information information1 = new Information(hit.getSource());
            String jsonStr = hit.getSourceAsString();
            if (jsonStr.contains(information.getMainType())) {
                arrayList.add(information1);
            }
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }



    public String searchTag(Information information) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            SearchResponse response = client.prepareSearch(information.getIndexName()).setTypes(information.getType())
                    .setQuery(QueryBuilders.matchAllQuery())
                    .execute().actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
            } else {
                for (int i = 0; i < resultHits.getHits().length; i++) {
                    String str = (String) resultHits.getHits()[i].getSource().get(information.getTag());
                    String jsonStr = "{\"title\":\"" + str + "\"}";
                    arrayList.add(jsonStr);
                }
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }

    public String searchAll(Information information) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            SearchResponse response = client.prepareSearch(information.getIndexName()).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
            } else {
                for (int i = 0; i < resultHits.getHits().length; i++) {
                    String jsonStr = resultHits.getHits()[i].getSourceAsString();
                    System.out.println(resultHits.getHits()[i].getId() + "\t" + jsonStr);
                    System.out.println();
                    arrayList.add(jsonStr);
                }
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }

    public String searchAlways(Information information) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            SearchResponse response = client.prepareSearch(information.getIndexName()).setTypes("always").setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
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

    public String searchByTitle(Information information) {
        ArrayList<String> arrayList = new ArrayList<>();
        SearchResponse myresponse = client.prepareSearch(information.getIndexName()).setQuery(QueryBuilders.matchPhraseQuery("title", information.getTitle())).execute().actionGet();
        SearchHits hits = myresponse.getHits();
        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsString());
            arrayList.add(hits.getHits()[i].getSourceAsString());
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }

    /**
     * 根据传入的关键字进行数据搜索，
     * 标红，
     * 根据score排序
     * 可分页
     * */
    public String searchByContent(Information information) {
        String listJson = "[]";
        try {
            ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
            SearchResponse myresponse = client.prepareSearch(information.getIndexName())
                    .setTypes(information.getType())
                    .addHighlightedField("title")
                    .setQuery(QueryBuilders.multiMatchQuery(information.getTitle(), "title"))
                    .setHighlighterPreTags("<span style='color: red'>")
                    .setHighlighterPostTags("</span>")
                    .setHighlighterRequireFieldMatch(false)
                    .addSort(SortBuilders.fieldSort("title"))
                    .addSort("score", SortOrder.DESC)
                    .execute()
                    .actionGet();
            SearchHits hits = myresponse.getHits();
            for (int i = 0; i < hits.getHits().length; i++) {
                Map<String, Object> source = hits.getHits()[i].getSource();
                Map<String, HighlightField> highlightFields = hits.getHits()[i].getHighlightFields();
                HighlightField titleField = highlightFields.get("title");
                if (titleField != null) {
                    Text[] titleFragments = titleField.fragments();
                    String tiTmp = "";
                    for (Text text : titleFragments) {
                        tiTmp += text;
                    }
                    //将高亮片段组装到结果中去
                    source.put("title", tiTmp);
                }
                source.put("id",hits.getHits()[i].getId());
                arrayList.add(source);
            }
            listJson = JSON.toJSONString(arrayList);
        }catch (Exception e){
            logger.error("fun:  searchByContent",e);
        }
        return listJson;
    }

    public String searchByType(Information information) {
            ArrayList<Information> arrayList = new ArrayList<>();
        try {
            SearchResponse response = client.prepareSearch(information.getIndexName())
                    .setTypes(information.getType())
                    .setQuery(QueryBuilders.matchAllQuery())
                    .execute()
                    .actionGet();
            SearchHits resultHits = response.getHits();
            if (resultHits.getHits().length == 0) {
                System.out.println("查到0条数据!");
            } else {
                for (SearchHit hit:resultHits) {
                    hit.getSource().put("id",hit.getId());
                    arrayList.add(information);
                }
            }
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
        String listJson = JSON.toJSONString(arrayList);
        return listJson;
    }

    public String deleteById(Information information) {
        String status = "No";
        try {
            DeleteResponse dResponse = client.prepareDelete(information.getIndexName(), information.getType(), information.getId()).execute().actionGet();
            if (dResponse.isFound()) {
                System.out.println("删除成功");
                status = "Ok";
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public UserDao searchAccount(UserDao userDao) {
        UserDao userDao1 = null;
        SearchResponse myresponse = client.prepareSearch(userDao.getIndexName()).setQuery(QueryBuilders.matchPhraseQuery("email", userDao.getEmail())).execute().actionGet();
        SearchHits hits = myresponse.getHits();
        if (hits.getHits().length == 1) {
            userDao1 = JSON.parseObject(hits.getHits()[0].getSourceAsString(), new TypeReference<UserDao>() {
            });
            return userDao1;
        }
        return userDao1;
    }

    @Override
    public String addUser(UserDao userDao) {
        if (isAccountExists(userDao)) {
            return "1";
        } else {
            try {
                String jsonStr = JSON.toJSONString(userDao);
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                bulkRequest.add(client.prepareIndex(userDao.getIndexName(), userDao.getType()).setSource(jsonStr));
                bulkRequest.execute().actionGet();
            } catch (Exception e) {
                e.printStackTrace();
                return "1";
            }
            return "0";
        }
    }

    @Override
    public String deleteUser(UserDao userDao) {
        String status = "0";
        try {
            DeleteByQueryResponse dResponse = client
                    .prepareDeleteByQuery(userDao.getIndexName())
                    .setTypes(userDao.getType())
                    .setQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("email", userDao.getEmail())))
                    .execute().actionGet();
            status = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public String upDateUser(UserDao userDao) {
        try {
            SearchResponse myresponse = client.prepareSearch(userDao.getIndexName()).setQuery(QueryBuilders.matchPhraseQuery("email", userDao.getEmail())).execute().actionGet();
            SearchHits hits = myresponse.getHits();
            String userId = hits.getHits()[0].getId();
            String userJson = JSON.toJSONString(userDao);
            UpdateResponse response = client.prepareUpdate(userDao.getIndexName(), userDao.getType(), userId)
                    .setDoc(userJson)
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            logger.error("Method: upDateUser\tpara userDao: " + userDao.toString());
            return "{\"status\":\"fail\"}";
        }
        return "{\"status\":\"ok\"}";
    }

    public String searchById(Information information) {
        GetResponse  myresponse = client.prepareGet(information.getIndexName(),information.getType(),information.getId()).get();
        return null;
    }

    /**
    * 根据返回id对该数据访问次数加1
    * */

    @Override
    public String upDateScore(Information information) {
        try {
            GetResponse myresponse = client.prepareGet(information.getIndexName(), information.getType(), information.getId()).get();
            myresponse.getSource().put("id", myresponse.getId());
            myresponse.getSource().put("type", myresponse.getType());
            System.out.println(myresponse.getSource());
            Information information1 = new Information(myresponse.getSource());
            information1.setScore((information1.getScore() + 1));
            String updateStr = JSON.toJSONString(information1);
            System.out.println(updateStr);
            UpdateResponse response = client.prepareUpdate(information1.getIndexName(), information1.getType(), information1.getId())
                    .setDoc(updateStr)
                    .execute()
                    .actionGet();
        }catch (Exception e){
            logger.error("fun:  upDateScore",e);
            return "{\"status\":\"fail\"}";
        }
        return "{\"status\":\"ok\"}";
    }

    public void searchDate(Information information){
        MatchQueryBuilder mpq1 = QueryBuilders .matchPhraseQuery("date",information.getDate());
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(mpq1);
        SearchResponse response = client.prepareSearch(information.getIndexName())
                .setTypes(information.getType())
                .setQuery(builder)
                .addSort("date", SortOrder.DESC)
                .execute()
                .actionGet();
        SearchHits searchHits = response.getHits();
        for (SearchHit hit : searchHits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 确认账户是否存在
     *
     * */
    public boolean isAccountExists(UserDao userDao) {
        SearchResponse myresponse = client.prepareSearch(userDao.getIndexName()).setQuery(QueryBuilders.matchPhraseQuery("email", userDao.getEmail())).execute().actionGet();
        SearchHits hits = myresponse.getHits();
        if (hits.getHits().length == 0) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ElasticSearchOpt elasticSearchOpt = new ElasticSearchOpt();
        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("type", "test");
//        hashMap.put("date","2018/10");
//        Information in = new Information(hashMap);
//        elasticSearchOpt.searchDate(in);
//        System.out.println(elasticSearchOpt.searchByContent(in));
//        elasticSearchOpt.upDateScore(in);
//        Date date = new Date();
//        System.out.println("++++++/"+date.getMonth()+"/"+date.getDate()+"  "+date.getHours()+":"+date.getMinutes());
//        SimpleDateFormat smp = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
//        String date1 = smp.format(date);
//        System.out.println(date1);
//        System.out.println(elasticSearchOpt.searchId(in));
//        System.out.println(elasticSearchOpt.searchSubType(in));
//        System.out.println(elasticSearchOpt.searchByContent(in));
//        System.out.println(JSON.toJSONString(in));
//        elasticSearchOpt.addJson(in);
//        hashMap.put("email", "15810227076@163.com");
//        hashMap.put("pass", "123");
//        UserDao userDao = new UserDao(hashMap);
//        UserDao userDao1 = elasticSearchOpt.searchAccount(userDao);
//        System.out.println(elasticSearchOpt.isAccountExists(userDao));
//        System.out.println(elasticSearchOpt.addUser(userDao));
//        System.out.println(elasticSearchOpt.upDateUser(userDao));
//        System.out.println(userDao1);
//        elasticSearchOpt.deleteUser(userDao);
//        elasticSearchOpt.delDocument(in);
    }
}