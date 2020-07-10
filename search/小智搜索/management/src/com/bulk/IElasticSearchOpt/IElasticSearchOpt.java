package com.bulk.IElasticSearchOpt;


import com.dao.Information;
import com.dao.MyRequest;
import com.dao.Question;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;

public interface IElasticSearchOpt {
     SearchHits searchByDate(Information information);
     String delDocument(Information information);
     String delQuestion(Question question);
     String deleteById(String id);
     String addJson(Information information);
     String addQuestion(Question question);
     String searchQuestion();
     String searchById(MyRequest myRequest);
     String searchQByType(MyRequest myRequest);
     String updateStatusById(MyRequest myRequest);
     SearchResponse searchFKui(MyRequest myRequest);
}
