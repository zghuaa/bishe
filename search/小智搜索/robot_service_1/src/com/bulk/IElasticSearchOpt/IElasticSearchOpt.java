package com.bulk.IElasticSearchOpt;


import com.Db.dao.Information;
import com.Db.dao.Question;
import com.Db.dao.UserDao;

public interface IElasticSearchOpt {
     String addJson(Information information);

     String addQuestion(Question question);

     String delDocument(Information information);

     String searchType(Information information);

     String searchTag(Information information);

     String searchAll(Information information);

     String searchAlways(Information information);

     String searchByTitle(Information information);

     String searchByContent(Information information);

     String searchByType(Information information);

     String deleteById(Information information) ;

     UserDao searchAccount(UserDao userDao);

     String addUser(UserDao userDao);

     String deleteUser(UserDao userDao);

     String upDateScore(Information information);

     String searchSubType(Information information);

     String upDateUser(UserDao userDao);

}
